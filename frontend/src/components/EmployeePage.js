import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import FaCircle from 'react-icons/lib/fa/circle';
import FaAdjust from 'react-icons/lib/fa/adjust';
import FaExclamationCircle from 'react-icons/lib/fa/exclamation-circle';
import FaCheckCircle from 'react-icons/lib/fa/check-circle';
import EmployeeTasksModal from './tasks/EmployeeTasksModal';

class EmployeePage extends React.Component {
  constructor(props) {
    super(props);

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
      tasksModal: false,
    };

    this.toggleTasksModal = this.toggleTasksModal.bind(this);
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

  toggleTasksModal() {
    console.log('toggle');
    this.setState({
      tasksModal: !this.state.tasksModal,
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

        <button onClick={this.toggleTasksModal}>Example Task Modal</button>
        <EmployeeTasksModal
          employeeId={this.state.employeeId}
          isOpen={this.state.tasksModal}
          parentToggle={this.toggleTasksModal}
        />
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
          case 'DONE':
            return <FaCheckCircle size={50} color="green" />;
          case 'CURRENT':
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
