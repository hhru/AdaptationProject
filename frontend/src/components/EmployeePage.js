import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import FaCircle from 'react-icons/lib/fa/circle';
import FaAdjust from 'react-icons/lib/fa/adjust';
import FaExclamationCircle from 'react-icons/lib/fa/exclamation-circle';
import FaCheckCircle from 'react-icons/lib/fa/check-circle';

class EmployeePage extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      employeeId: this.props.location.search,
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
    };
  }

  componentDidMount() {
    let responseData = {
      id: 1,
      employee: {
        id: 3,
        firstName: 'Джон',
        lastName: 'МакКлейн',
        middleName: 'Иванович',
        email: 'die@hard.com',
        inside: 'john',
      },
      chief: {
        id: 1,
        firstName: 'Гусев',
        lastName: 'Леонид',
        middleName: 'Викторович',
        email: 'l.gusev@hh.ru',
        inside: 'gusev',
      },
      mentor: {
        id: 1,
        firstName: 'Гусев',
        lastName: 'Леонид',
        middleName: 'Викторович',
        email: 'l.gusev@hh.ru',
        inside: 'gusev',
      },
      hr: {
        id: 1,
        firstName: 'Билл',
        lastName: 'Гейтс',
        middleName: 'В.',
        email: 'gates@microsoft.com',
        inside: 'gates',
      },
      employmentDate: '2018-03-31',
      currentWorkflowStep: 'TASK_LIST',
      workflow: [
        {
          id: 8,
          type: 'ADD',
          status: 'COMPLETE',
          deadlineDate: '2018-03-31',
          comment: null,
          overdue: false,
        },
        {
          id: 7,
          type: 'TASK_LIST',
          status: 'COMPLETE',
          deadlineDate: '2018-03-31',
          comment: null,
          overdue: false,
        },
        {
          id: 6,
          type: 'WELCOME_MEETING',
          status: 'IN_PROGRESS',
          deadlineDate: '2018-03-31',
          comment: null,
          overdue: false,
        },
        {
          id: 5,
          type: 'INTERIM_MEETING',
          status: 'NOT_DONE',
          deadlineDate: '2018-03-31',
          comment: null,
          overdue: true,
        },
        {
          id: 4,
          type: 'INTERIM_MEETING_RESULT',
          status: 'NOT_DONE',
          deadlineDate: '2018-03-31',
          comment: null,
          overdue: false,
        },
        {
          id: 3,
          type: 'FINAL_MEETING',
          status: 'NOT_DONE',
          deadlineDate: '2018-03-31',
          comment: null,
          overdue: false,
        },
        {
          id: 2,
          type: 'FINAL_MEETING_RESULT',
          status: 'NOT_DONE',
          deadlineDate: '2018-03-31',
          comment: null,
          overdue: false,
        },
        {
          id: 1,
          type: 'QUESTIONNAIRE',
          status: 'NOT_DONE',
          deadlineDate: '2018-03-31',
          comment: null,
          overdue: false,
        },
      ],
    };
    //$.get('/api/employee/' + sthis.state.employeeId);
    this.setState({
      data: responseData,
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
      <div>
        <p>
          {' '}
          {`${employeeFirstName} ${employeeMiddleName} ${employeeLastName}`}{' '}
        </p>
        <p> {employeeEmail} </p>
        <p>
          {' '}
          {`Начальник: ${chiefFirstName} ${chiefMiddleName} ${chiefLastName}`}{' '}
        </p>
        <p>
          {' '}
          {`Ментор: ${mentorFirstName} ${mentorMiddleName} ${mentorLastName}`}{' '}
        </p>
        <p> {`HR: ${hrFirstName} ${hrMiddleName} ${hrLastName}`} </p>
        <p> {`Дата выхода: ${employmentDate}`} </p>
        <Workflow data={workflow} />
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
    console.log(status, overdue);
    switch (overdue) {
      case true:
        return <FaExclamationCircle size={50} color="red" />;
      default:
        switch (status) {
          case 'COMPLETE':
            return <FaCheckCircle size={50} color="green" />;
          case 'IN_PROGRESS':
            return <FaAdjust size={50} color="yellow" />;
          default:
            return <FaCircle size={50} color="grey" />;
        }
    }
  }

  render() {
    const { deadlineDate, status, overdue, type } = this.props;

    return (
      <div>
        {deadlineDate}
        {this.selectIcon(status, overdue)}
        {type}
        <br />
      </div>
    );
  }
}

export default EmployeePage;
