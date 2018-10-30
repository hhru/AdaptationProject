import React from 'react';
import axios from 'axios';
import FaFilter from 'react-icons/lib/fa/filter';
import { Progress, Row, Col, Form, FormGroup, Label, Input } from 'reactstrap';
import ReactTable from 'react-table';

import '!style-loader!css-loader!react-table/react-table.css';
import '!style-loader!css-loader!bootstrap/dist/css/bootstrap.css';
import '!style-loader!css-loader!./ListEmployees.css';

const PAGE_PARAMS_LOCAL_STORAGE_KEY = 'adaptEmployeeListParams';

class ListEmployees extends React.Component {
  loadPageParams = () => {
    const pageParams = JSON.parse(localStorage.getItem(PAGE_PARAMS_LOCAL_STORAGE_KEY)) || {
      filterable: false,
      defaultPageSize: 20,
    };

    return pageParams;
  };

  savePageParams = (filterable, pageSize) => {
    localStorage.setItem(
      PAGE_PARAMS_LOCAL_STORAGE_KEY,
      JSON.stringify({
        filterable: filterable,
        pageSize: pageSize,
      })
    );
  };

  getFilterColor = (filterable) => {
    return filterable ? '#a0a0a0' : '#e6e6e6';
  };

  initialPageParams = this.loadPageParams();

  state = {
    isLoading: true,
    filterable: this.initialPageParams.filterable,
    defaultPageSize: this.initialPageParams.pageSize,
    filterColor: this.getFilterColor(this.initialPageParams.filterable),
    showDismissed: false,
    showCompleted: false,
    showActive: true,
    employeeList: [],
  };

  handleButtonClick = (e, row) => {
    this.props.history.push('/employee/' + row.id);
  };

  onFiltered = () => {
    const filterable = !this.state.filterable;

    this.savePageParams(filterable, this.refs.table.state.pageSize);

    this.setState({
      filterable: filterable,
      filterColor: this.getFilterColor(filterable),
    });
  };

  onPageSizeChange = (pageSize) => {
    this.savePageParams(this.state.filterable, pageSize);
  };

  onShowCompleted = (e) => {
    this.setState({
      showCompleted: !this.state.showCompleted,
    });
  };

  onShowActive = (e) => {
    this.setState({
      showActive: !this.state.showActive,
    });
  };

  onShowDismissed = (e) => {
    this.setState({
      showDismissed: !this.state.showDismissed,
    });
  };

  filteredData = () => {
    let result = this.state.employeeList;

    if (!this.state.showActive) {
      result = result.filter((e) => {
        const curStep = e.workflow.filter((w) => w.status == 'CURRENT');
        return e.dismissed ? true : curStep.length === 0;
      });
    }

    if (!this.state.showCompleted) {
      result = result.filter((e) => {
        const curStep = e.workflow.filter((w) => w.status == 'CURRENT');
        //return curStep.length == 0 ? false : true;
        return e.dismissed ? true : curStep.length !== 0;
      });
    }

    if (!this.state.showDismissed) {
      result = result.filter((e) => !e.dismissed);
    }

    return result;
  };

  customFilter = (filter, row, column) => {
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
  };

  getTrProps = (state, rowInfo) => {
    return rowInfo == undefined
      ? {}
      : {
          className: 'clickable',
          onClick: (e, handleOriginal) => {
            this.props.history.push('/employee/' + rowInfo.row._original.id);
          },
        };
  };

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

  render() {
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
        <Row className="mb-1">
          <Col sm={{ size: 2, offset: 0 }}>
            <span className="text-muted cur-pointer underline" onClick={this.onFiltered}>
              <FaFilter size={20} color={this.state.filterColor} onClick={this.onFiltered} />
              &nbsp;Фильтр
            </span>
          </Col>

          {this.state.filterable && (
            <Col sm={{ size: 2, offset: 3 }}>
              <Form>
                <FormGroup check className="text-muted">
                  <Label check>
                    <Input
                      type="checkbox"
                      checked={this.state.showActive}
                      onChange={this.onShowActive}
                    />{' '}
                    <span>Активные</span>
                  </Label>
                </FormGroup>
              </Form>
            </Col>
          )}

          {this.state.filterable && (
            <Col sm={{ size: 2 }}>
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
          )}

          {this.state.filterable && (
            <Col sm={{ size: 3 }}>
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
          )}
        </Row>

        <ReactTable
          filterable={this.state.filterable}
          defaultPageSize={this.state.defaultPageSize}
          loading={this.state.isLoading}
          data={data}
          columns={columns}
          getTrProps={this.getTrProps}
          defaultFilterMethod={this.customFilter}
          onPageSizeChange={this.onPageSizeChange}
          ref="table"
        />
      </div>
    );
  }
}

export default ListEmployees;
