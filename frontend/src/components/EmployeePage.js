import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import FaCircle from 'react-icons/lib/fa/circle';
import FaAdjust from 'react-icons/lib/fa/adjust';
import FaExclamationCircle from 'react-icons/lib/fa/exclamation-circle';
import FaCheckCircle from 'react-icons/lib/fa/check-circle';

import '!style-loader!css-loader!./app.css';

class EmployeePage extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      employeeId: this.props.match.params.id,
      data: {
        id: 1,
        currentWorkflowStep: 'ADD',
        employee: {
          id: 1,
          firstName: 'Jason',
          lastName: 'Statham',
          middleName: 'Brutal',
          email: 'die@hard.com',
          inside: 'inside',
        },
        chief: {
          id: 1,
          firstName: 'Your',
          lastName: 'Boss',
          middleName: 'Topbanana',
          email: 'awdwada@www.rr',
          inside: 'inside2',
        },
        mentor: {
          id: 2,
          firstName: 'Some',
          lastName: 'Qqq',
          middleName: 'Chuvachek',
          email: 'awdwd@aad.tt',
          inside: 'inside3',
        },
        hr: {
          id: 3,
          firstName: 'Peter',
          lastName: 'Parker',
          middleName: 'Spider',
          email: 'spiderm@il.web',
          inside: 'inside4',
        },
        employmentDate: null,
        workflow: [
          {
            id: 1,
            type: 'ADD',
            status: 'DONE',
            deadlineDate: null,
            comment: null,
            overdue: false,
          },
          {
            id: 2,
            type: 'TASK_LIST',
            status: 'DONE',
            deadlineDate: null,
            comment: null,
            overdue: false,
          },
          {
            id: 3,
            type: 'WELCOME_MEETING',
            status: 'CURRENT',
            deadlineDate: null,
            comment: null,
            overdue: false,
          },
          {
            id: 4,
            type: 'INTERIM_MEETING',
            status: 'NOT_DONE',
            deadlineDate: null,
            comment: null,
            overdue: false,
          },
          {
            id: 5,
            type: 'INTERIM_MEETING_RESULT',
            status: 'NOT_DONE',
            deadlineDate: null,
            comment: null,
            overdue: false,
          },
          {
            id: 6,
            type: 'FINAL_MEETING',
            status: 'NOT_DONE',
            deadlineDate: null,
            comment: null,
            overdue: false,
          },
          {
            id: 7,
            type: 'FINAL_MEETING_RESULT',
            status: 'NOT_DONE',
            deadlineDate: null,
            comment: null,
            overdue: false,
          },
          {
            id: 8,
            type: 'QUESTIONNAIRE',
            status: 'NOT_DONE',
            deadlineDate: null,
            comment: null,
            overdue: false,
          },
        ],
      },
    };
    /*
    this.state = {
      employeeId: this.props.match.params.id,
      data: {
        id: null,
        currentWorkflowStep: '',
        employee: {
          id: null,
          firstName: '',
          lastName: '',
          middleName: '',
          email: '',
          inside: '',
        },
        chief: {
          id: null,
          firstName: '',
          lastName: '',
          middleName: '',
          email: '',
          inside: '',
        },
        mentor: {
          id: null,
          firstName: '',
          lastName: '',
          middleName: '',
          email: '',
          inside: '',
        },
        hr: {
          id: null,
          firstName: '',
          lastName: '',
          middleName: '',
          email: '',
          inside: '',
        },
        employmentDate: null,
        workflow: [
          {
            id: null,
            type: '',
            status: '',
            deadlineDate: null,
            comment: null,
            overdue: false,
          },
        ],
      },
    };*/
  }

  componentDidMount() {
    const url = '/api/employee/' + this.state.employeeId;
    const self = this;
    axios
      .get(url)
      .then(function(response) {
        console.log(response.data);
        self.setState({
          data: response.data,
        });
      })
      .catch(function(error) {
        console.log(error);
      });
  }

  render() {
    const {
      firstName: employeeFirstName,
      middleName: employeeMiddleName,
      lastName: employeeLastName,
      email: employeeEmail,
    } = this.state.data.employee;
    const {
      firstName: chiefFirstName,
      middleName: chiefMiddleName,
      lastName: chiefLastName,
    } = this.state.data.chief;
    const {
      firstName: mentorFirstName,
      middleName: mentorMiddleName,
      lastName: mentorLastName,
    } = this.state.data.mentor;
    const {
      firstName: hrFirstName,
      middleName: hrMiddleName,
      lastName: hrLastName,
    } = this.state.data.hr;
    const employmentDate = this.state.data.employmentDate;
    const workflow = this.state.data.workflow;

    return (
      <div class="container">
        <div class="jumbotron p-md-5 rounded">
          <div class="row mb-3">
            <div class="col-md-1" />
            <div class="col-md-7">
              <h3 class="mb-0 font-weight-bold">
                {' '}
                {`${employeeFirstName} ${employeeMiddleName} ${employeeLastName}`}{' '}
              </h3>
              <div class="mb-1 ml-2 text-info"> {employeeEmail} </div>
              <br />
              <div class="ml-4">
                <p class="mb-2 text-muted">
                  {' '}
                  {`Начальник: ${chiefFirstName} ${chiefMiddleName} ${chiefLastName}`}{' '}
                </p>
                <p class="mb-2 text-muted">
                  {' '}
                  {`Ментор: ${mentorFirstName} ${mentorMiddleName} ${mentorLastName}`}{' '}
                </p>
                <p class="text-muted">
                  {`HR: ${hrFirstName} ${hrMiddleName} ${hrLastName}`}
                </p>
              </div>
            </div>
            <div class="col-md-4 mt-5 blog-sidebar">
              <div class="p-2">
                <p class="font-italic"> {`Дата выхода: ${employmentDate}`} </p>
                <p class="font-italic"> {`Остлаось: 5 дн.`} </p>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-1" />
            <div class="col-md-5 mt-5">
              <Workflow data={workflow} />
            </div>
            <div class="col-md-5">
              <div class="ml-2">
                <h4>
                  <span class="text-muted">Комментарии</span>
                </h4>
              </div>
              <div>
                <ul class="list-group mb-0 anyClass">
                  <li class="list-group-item d-flex justify-content-between lh-condensed">
                    <div>
                      <h6 class="my-0 mb-2">Pechkin:</h6>
                      <span>
                        Тот чувак с бородой с 3-го этажа. А что если здесь будет
                        много текста который наверняка не влезет в одну строчку
                        и браузеру придется его переносить на новую строку
                      </span>
                    </div>
                    <span class="text-muted">tag1</span>
                  </li>
                  <li class="list-group-item d-flex justify-content-between lh-condensed">
                    <div>
                      <h6 class="my-0">Pechkin:</h6>
                      <span>vnature chetko</span>
                    </div>
                    <span class="text-muted">tag1</span>
                  </li>
                  <li class="list-group-item d-flex justify-content-between lh-condensed">
                    <div>
                      <h6 class="my-0">Pechkin:</h6>
                      <span>vnature chetko</span>
                    </div>
                    <span class="text-muted">tag1</span>
                  </li>
                  <li class="list-group-item d-flex justify-content-between lh-condensed">
                    <div>
                      <h6 class="my-0">Pechkin:</h6>
                      <span>vnature chetko</span>
                    </div>
                    <span class="text-muted">tag1</span>
                  </li>
                  <li class="list-group-item d-flex justify-content-between lh-condensed">
                    <div>
                      <h6 class="my-0">Pechkin:</h6>
                      <span>vnature chetko</span>
                    </div>
                    <span class="text-muted">tag1</span>
                  </li>
                  <li class="list-group-item d-flex justify-content-between lh-condensed">
                    <div>
                      <h6 class="my-0">Pechkin:</h6>
                      <span>vnature chetko</span>
                    </div>
                    <span class="text-muted">tag1</span>
                  </li>
                </ul>
                <form>
                  <input class="form-control" placeholder="" type="text" />
                </form>
              </div>
            </div>
            <div class="col-md-1" />
          </div>
        </div>
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
  }

  selectIcon(status, overdue) {
    switch (overdue) {
      case true:
        return <FaExclamationCircle size={50} color="red" />;
      default:
        switch (status) {
          case 'DONE':
            return <FaCheckCircle size={50} color="green" />;
          case 'CURRENT':
            return <FaAdjust size={50} color="yellow" />;
          default:
            return <FaCircle size={50} color="grey" />;
        }
    }
  }

  typeTranslate(type) {
    switch (type) {
      case 'ADD':
        return <span>Подготовка к сопровождению</span>;
      case 'TASK_LIST':
        return <span>Задачи на испытательный срок</span>;
      case 'WELCOME_MEETING':
        return <span>Велком встреча</span>;
      case 'INTERIM_MEETING':
        return <span>Промежуточная встреча</span>;
      case 'INTERIM_MEETING_RESULT':
        return <span>Результаты промежуточной встречи</span>;
      case 'FINAL_MEETING':
        return <span>Итоговая встреча</span>;
      case 'FINAL_MEETING_RESULT':
        return <span>Результаты итоговой встречи</span>;
      case 'QUESTIONNAIRE':
        return <span>Опросник</span>;
    }
  }

  render() {
    const { deadlineDate, status, overdue, type } = this.props;

    return (
      <div>
        {deadlineDate}
        {this.selectIcon(status, overdue)}
        {this.typeTranslate(type)}
        <br />
      </div>
    );
  }
}

export default EmployeePage;
