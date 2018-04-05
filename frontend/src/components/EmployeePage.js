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
    const employee = `${this.state.data.employee.firstName} ${
      this.state.data.employee.middleName
    } ${this.state.data.employee.lastName}`;
    const employeeEmail = this.state.data.employee.email;
    const chief = `${this.state.data.chief.firstName} ${
      this.state.data.chief.middleName
    } ${this.state.data.chief.lastName}`;
    const mentor = `${this.state.data.mentor.firstName} ${
      this.state.data.mentor.middleName
    } ${this.state.data.mentor.lastName}`;
    const hr = `${this.state.data.hr.firstName} ${
      this.state.data.hr.middleName
    } ${this.state.data.hr.lastName}`;
    const employmentDate = this.state.data.employmentDate;
    const workflow = this.state.data.workflow;

    return (
      <div>
        <p>{employee}</p>
        <p>{employeeEmail}</p>
        <p>{`Начальник: ${chief}`}</p>
        <p>{`Ментор: ${mentor}`}</p>
        <p>{`HR: ${hr}`}</p>
        <p>{`Дата выхода: ${employmentDate}`}</p>
        <Workflow data={workflow} />
      </div>
    );
  }
}

class Workflow extends React.Component {
  constructor(props) {
    super(props);

    this.renderWorkflowStage = this.renderWorkflowStage.bind(this);
  }

  renderWorkflowStage(workflowStage) {
    const iconMap = {
      IN_PROGRESS_OVERDUE: <FaExclamationCircle size={50} color="red" />,
      COMPLETE_OVERDUE: <FaExclamationCircle size={50} color="red" />,
      NOT_DONE_OVERDUE: <FaExclamationCircle size={50} color="red" />,
      IN_PROGRESS: <FaAdjust size={50} color="yellow" />,
      COMPLETE: <FaCheckCircle size={50} color="green" />,
      NOT_DONE: <FaCircle size={50} color="grey" />,
    };

    return (
      <div>
        {workflowStage.deadlineDate}
        {
          iconMap[
            workflowStage['status'] +
              (workflowStage['overdue'] == true ? '_OVERDUE' : '')
          ]
        }
        {workflowStage.type}
        <br />
      </div>
    );
  }

  render() {
    return (
      <div>
        {this.props.data.map((workflowStage) =>
          this.renderWorkflowStage(workflowStage)
        )}
      </div>
    );
  }
}

export default EmployeePage;
