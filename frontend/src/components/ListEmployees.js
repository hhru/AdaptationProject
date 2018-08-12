import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import FaCircle from 'react-icons/lib/fa/circle';
import FaAdjust from 'react-icons/lib/fa/adjust';
import FaExclamationCircle from 'react-icons/lib/fa/exclamation-circle';
import FaCheckCircle from 'react-icons/lib/fa/check-circle';
import FaFilter from 'react-icons/lib/fa/filter';
import { Progress, Container, Row, Col, Form, FormGroup, Label, Input } from 'reactstrap';
import ReactTable from 'react-table';

import '!style-loader!css-loader!react-table/react-table.css';
import '!style-loader!css-loader!bootstrap/dist/css/bootstrap.css';
import '!style-loader!css-loader!./ListEmployees.css';
import classnames from 'classnames';

class ListEmployees extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      filterable: false,
      filterColor: '#c6c6c6',
      showDismissed: false,
      showCompleted: false,
      employeeList: [],
    };

    this.handleButtonClick = this.handleButtonClick.bind(this);
    this.onFiltered = this.onFiltered.bind(this);
    this.onShowCompleted = this.onShowCompleted.bind(this);
    this.onShowDismissed = this.onShowDismissed.bind(this);
    this.filteredData = this.filteredData.bind(this);
    this.customFilter = this.customFilter.bind(this);
    this.getTrProps = this.getTrProps.bind(this);
  }

  handleButtonClick(e, row) {
    this.props.history.push('/employee/' + row.id);
  }

  onFiltered() {
    this.setState({
      filterable: !this.state.filterable,
      filterColor: this.state.filterable ? '#c6c6c6' : '#a0a0a0',
    });
  }

  componentDidMount() {
    axios
      .get('/api/employee/all')
      .then((response) => {
        this.setState({
          isLoading: false,
          employeeList: response.data,
        });
      })
      .catch((error) => {
        this.setState({
          isLoading: false,
        });
        console.log(error);
      });
  }

  onShowCompleted(e) {
    this.setState({
      showCompleted: !this.state.showCompleted,
    });
  }

  onShowDismissed(e) {
    this.setState({
      showDismissed: !this.state.showDismissed,
    });
  }

  filteredData() {
    let result = this.state.employeeList;

    if (!this.state.showCompleted) {
      result = result.filter((e) => {
        const curStep = e.workflow.filter((w) => w.status == 'CURRENT');
        return curStep.length == 0 ? false : true;
      });
    }

    if (!this.state.showDismissed) {
      result = result.filter((e) => !e.dismissed);
    }
    return result;
  }

  customFilter(filter, row, column) {
    const id = filter.pivotId || filter.id;
    if (row[id] === undefined) {
      return true;
    }
    let str;
    if (id === 'progress') {
      str = row.progress.props.children[1];
    } else if (id === 'date') {
      str = row.date.props.children;
    } else {
      str = String(row[id]);
    }
    const filt = filter.value.toUpperCase();
    return str.toUpperCase().search(filt) != -1;
  }

  getTrProps(state, rowInfo) {
    return rowInfo == undefined
      ? {}
      : {
          className: 'clickable',
          onClick: (e, handleOriginal) => {
            this.props.history.push('/employee/' + rowInfo.row._original.id);
          },
        };
  }

  render() {
    let rowIndex = 0;

    const fullTextToDisplay = {
      ADD: 'Добавление в систему',
      TASK_LIST: 'Постановка задач',
      WELCOME_MEETING: 'Welcome встреча',
      INTERIM_MEETING: 'Промежуточная встреча',
      INTERIM_MEETING_RESULT: 'Итоги промежуточной встречи',
      FINAL_MEETING: 'Финальная встреча',
      FINAL_MEETING_RESULT: 'Итоги финальной встречи',
      QUESTIONNAIRE: 'Подведение итогов ИС',
      NONE: 'ИС завершен',
    };

    const progressPercent = {
      ADD: 5,
      TASK_LIST: 10,
      WELCOME_MEETING: 20,
      INTERIM_MEETING: 40,
      INTERIM_MEETING_RESULT: 50,
      FINAL_MEETING: 70,
      FINAL_MEETING_RESULT: 80,
      QUESTIONNAIRE: 90,
      NONE: 100,
    };

    let columns = [
      {
        Header: () => {
          return (
            <FaFilter
              size={16}
              color={this.state.filterColor}
              className="cur-pointer"
              onClick={this.onFiltered}
            />
          );
        },
        id: 'number',
        sortable: false,
        filterable: false,
        width: 35,
        resizable: false,
        Cell: (row) => {
          const num = ++rowIndex;
          if (rowIndex == this.state.employeeList.length) {
            rowIndex = 0;
          }
          return <span className="text-muted ml-1">{num}</span>;
        },
      },
      {
        Header: 'ФИО',
        id: 'fullName',
        minWidth: 150,
        accessor: (row) =>
          `${row.employee.firstName} ${
            row.employee.middleName == null ? '' : row.employee.middleName
          } ${row.employee.lastName}`,
      },
      {
        Header: 'HR',
        id: 'hrName',
        width: 280,
        accessor: (row) =>
          `${row.hr.firstName} ${row.hr.middleName == null ? '' : row.hr.middleName} ${
            row.hr.lastName
          }`,
      },
      {
        Header: 'Этап',
        id: 'progress',
        width: 350,
        accessor: (row) => {
          const cut = row.workflow.filter((x) => x.status == 'CURRENT');
          const curStep = cut.length == 0 ? 'NONE' : cut[0].type;
          const color = cut.length == 0 ? 'success' : cut[0].overdue ? 'danger' : 'success';
          return (
            <div>
              <div className="progress-bar mt-1">
                <Progress color={color} value={progressPercent[curStep]} />
              </div>

              {fullTextToDisplay[curStep]}
            </div>
          );
        },
      },
      {
        Header: 'Окончание',
        id: 'date',
        width: 140,
        accessor: (row) => {
          let dateOptions = {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
          };
          let endTime = new Date(row.employmentDate);
          endTime = new Date(endTime.setMonth(endTime.getMonth() + 3));
          return <div className="text-center">{endTime.toLocaleString('ru', dateOptions)}</div>;
        },
      },
    ];

    const data = this.filteredData();

    return (
      <div>
        {this.state.filterable && (
          <Row className="mb-1">
            <Col sm={{ size: 2, offset: 8 }}>
              <Form>
                <FormGroup check className="text-muted">
                  <Label check>
                    <Input
                      type="checkbox"
                      checked={this.state.showCompleted}
                      onChange={this.onShowCompleted}
                    />{' '}
                    <span>Прошедшие</span>
                  </Label>
                </FormGroup>
              </Form>
            </Col>

            <Col sm={{ size: 2 }}>
              <Form>
                <FormGroup check className="text-muted">
                  <Label check>
                    <Input
                      type="checkbox"
                      checked={this.state.showDismissed}
                      onChange={this.onShowDismissed}
                    />{' '}
                    <span>Не прошедшие ИС</span>
                  </Label>
                </FormGroup>
              </Form>
            </Col>
          </Row>
        )}

        <ReactTable
          filterable={this.state.filterable}
          loading={this.state.isLoading}
          data={data}
          columns={columns}
          getTrProps={this.getTrProps}
          defaultFilterMethod={this.customFilter}
        />
      </div>
    );
  }
}

export default ListEmployees;
