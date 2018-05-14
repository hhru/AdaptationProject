import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import FaCircle from 'react-icons/lib/fa/circle';
import FaAdjust from 'react-icons/lib/fa/adjust';
import FaExclamationCircle from 'react-icons/lib/fa/exclamation-circle';
import FaCheckCircle from 'react-icons/lib/fa/check-circle';
import { Progress, Container, Row, Col } from 'reactstrap';
import ReactTable from 'react-table';

import '!style-loader!css-loader!react-table/react-table.css';
import '!style-loader!css-loader!bootstrap/dist/css/bootstrap.css';
import '!style-loader!css-loader!./ListEmployees.css';
import classnames from 'classnames';

class ListEmployees extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      employeeList: [],
    };

    this.handleButtonClick = this.handleButtonClick.bind(this);
  }

  handleButtonClick(e, row) {
    this.props.history.push('/employee/' + row.id);
  }

  componentDidMount() {
    const url = '/api/employee/all';
    const self = this;
    axios
      .get(url)
      .then(function(response) {
        console.log(response.data);
        self.setState({
          employeeList: response.data,
        });
      })
      .catch(function(error) {
        console.log(error);
      });
  }

  render() {
    const colorsMap = {
      CURRENT_OVERDUE: 'danger',
      DONE_OVERDUE: 'danger',
      NOT_DONE_OVERDUE: 'danger',
      CURRENT: 'warning',
      DONE: 'success',
      NOT_DONE: 'light',
    };

    const shortTextToDisplay = {
      ADD: 1,
      TASK_LIST: 2,
      WELCOME_MEETING: 3,
      INTERIM_MEETING: 4,
      INTERIM_MEETING_RESULT: 5,
      FINAL_MEETING: 6,
      FINAL_MEETING_RESULT: 7,
      QUESTIONNAIRE: 8,
    };

    const fullTextToDisplay = {
      ADD: 'Добавлен в систему',
      TASK_LIST: 'Задачи поставлены',
      WELCOME_MEETING: 'Welcome встреча',
      INTERIM_MEETING: 'Промежуточная встреча',
      INTERIM_MEETING_RESULT: 'Результаты промежуточной встречи',
      FINAL_MEETING: 'Финальная встреча',
      FINAL_MEETING_RESULT: 'Результаты финальной встречи',
      QUESTIONNAIRE: 'Опрос',
    };

    let columns = [
      {
        Header: 'ФИО',
        id: 'fullName',
        accessor: (row) =>
          `${row.employee.firstName} ${
            row.employee.middleName == null ? '' : row.employee.middleName
          } ${row.employee.lastName}`,
      },
      {
        Header: 'Дата выхода',
        accessor: 'employmentDate',
      },
      {
        Header: 'Имя HR',
        id: 'hrName',
        accessor: (row) =>
          `${row.hr.firstName} ${row.hr.middleName == null ? '' : row.hr.middleName} ${
            row.hr.lastName
          }`,
      },
      {
        Header: 'Состояние',
        accessor: 'progress',
        Cell: (row) => (
          <div>
            <div className="text-center">{fullTextToDisplay[row.original.currentWorkflowStep]}</div>

            <Progress multi>
              {row.original.workflow.map((workflowStage) => (
                <Progress
                  bar
                  color={
                    colorsMap[
                      workflowStage['status'] + (workflowStage['overdue'] == true ? '_OVERDUE' : '')
                    ]
                  }
                  value={100.0 / row.original.workflow.length}
                  key={workflowStage.id}
                >
                  {shortTextToDisplay[workflowStage['type']]}
                </Progress>
              ))}
            </Progress>
          </div>
        ),
      },
      {
        Header: 'Перейти на страницу',
        id: 'fullPageButton',
        accessor: (row) => <button onClick={(e) => this.handleButtonClick(e, row)}>Info</button>,
      },
    ];

    return (
      <div>
        <ReactTable
          data={this.state.employeeList}
          columns={columns}
          SubComponent={(row) => {
            return <EmployeePageShort data={row.original} />;
          }}
        />
      </div>
    );
  }
}

class EmployeePageShort extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    const {
      firstName: employeeFirstName,
      middleName: employeeMiddleName,
      lastName: employeeLastName,
      email: employeeEmail,
      inside: employeeInside,
    } = this.props.data.employee;
    const {
      firstName: chiefFirstName,
      middleName: chiefMiddleName,
      lastName: chiefLastName,
    } = this.props.data.chief;
    const mentorFirstName =
      this.props.data.mentor != null ? this.props.data.mentor.firstName : null;
    const mentorMiddleName =
      this.props.data.mentor != null ? this.props.data.mentor.middleName : null;
    const mentorLastName = this.props.data.mentor != null ? this.props.data.mentor.lastName : null;
    const {
      firstName: hrFirstName,
      middleName: hrMiddleName,
      lastName: hrLastName,
    } = this.props.data.hr;

    const employmentDate = this.props.data.employmentDate;
    let employmentDateParsed = new Date(employmentDate);
    let lastProbationDate = new Date(employmentDate);
    lastProbationDate = new Date(lastProbationDate.setMonth(employmentDateParsed.getMonth() + 3));
    let dateOptions = {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      weekday: 'short',
    };

    const workflow = this.props.data.workflow;

    return (
      <div className="workflow-horizontal">
        <Container>
          <Row className="mb-4">
            <Col sm={{ size: 5 }}>
              <h5 className="mb-0 font-weight-bold">
                {`${employeeFirstName} ${employeeMiddleName} ${employeeLastName}`}
              </h5>
              <div className="mb-1 ml-2 text-info"> {employeeEmail} </div>
            </Col>
          </Row>

          <Row className="mb-2">
            <Col sm={{ size: 5 }}>
              <div className="ml-4">
                <p className="mb-2 text-muted">
                  {`Начальник: ${chiefFirstName} ${
                    chiefMiddleName == null ? '' : chiefMiddleName
                  } ${chiefLastName}`}
                </p>
                {this.props.data.mentor != null && (
                  <p className="mb-2 text-muted">
                    {`Ментор: ${mentorFirstName} ${
                      mentorMiddleName == null ? '' : mentorMiddleName
                    } ${mentorLastName}`}
                  </p>
                )}
                <p className="text-muted">
                  {`HR: ${hrFirstName} ${hrMiddleName == null ? '' : hrMiddleName} ${hrLastName}`}
                </p>
              </div>
            </Col>

            <Col sm={{ size: 5 }} className="mt-0 ml-5">
              <div className="">
                <p className="mb-2">
                  {`Дата выхода на работу: ${employmentDateParsed.toLocaleString(
                    'ru',
                    dateOptions
                  )}`}
                </p>
                <p className="mb-2">{`Дата окончания ИС: ${lastProbationDate.toLocaleString(
                  'ru',
                  dateOptions
                )}`}</p>
              </div>
            </Col>
          </Row>
          <Row>
            <Workflow data={workflow} />
          </Row>
        </Container>
      </div>
    );
  }
}

class Workflow extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        {this.props.data.map((workflowStageData) => (
          <WorkflowStage
            deadlineDate={workflowStageData.deadlineDate}
            status={workflowStageData.status}
            overdue={workflowStageData.overdue}
            type={workflowStageData.type}
            key={workflowStageData.id}
          />
        ))}
      </div>
    );
  }
}

class WorkflowStage extends React.Component {
  constructor(props) {
    super(props);

    this.selectIcon = this.selectIcon.bind(this);
    this.typeTranslate = this.typeTranslate.bind(this);
  }

  selectIcon(status, overdue) {
    switch (overdue) {
      case true:
        return <FaExclamationCircle size={30} color="#dc3545" />;
      default:
        switch (status) {
          case 'DONE':
            return <FaCheckCircle size={30} color="#28a745" />;
          case 'CURRENT':
            return <FaAdjust size={30} color="#ffc107" />;
          default:
            return <FaCircle size={30} color="#e2e3e5" />;
        }
    }
  }

  typeTranslate(type) {
    switch (type) {
      case 'ADD':
        return 'Добавлен в систему';
      case 'TASK_LIST':
        return 'Согласование задач';
      case 'WELCOME_MEETING':
        return 'Welcome-встреча';
      case 'INTERIM_MEETING':
        return 'Промежуточная встреча';
      case 'INTERIM_MEETING_RESULT':
        return 'Результаты промежуточной встречи';
      case 'FINAL_MEETING':
        return 'Итоговая встреча';
      case 'FINAL_MEETING_RESULT':
        return 'Результаты итоговой встречи';
      case 'QUESTIONNAIRE':
        return 'Опросник новичка';
    }
  }

  render() {
    const { status, overdue, type } = this.props;
    const currentText = status == 'CURRENT' ? this.typeTranslate(type) : '';

    return (
      <div
        className={
          'workflow-stage-horizontal ' +
          (status == 'CURRENT' ? 'workflow-current-stage-horizontal' : '')
        }
      >
        {this.selectIcon(status, overdue)}
        {currentText}
      </div>
    );
  }
}

export default ListEmployees;
