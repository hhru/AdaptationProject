import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import FaCircle from 'react-icons/lib/fa/circle';
import FaAdjust from 'react-icons/lib/fa/adjust';
import FaExclamationCircle from 'react-icons/lib/fa/exclamation-circle';
import FaCheckCircle from 'react-icons/lib/fa/check-circle';
import { Progress } from 'reactstrap';
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
      IN_PROGRESS_OVERDUE: 'danger',
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
        accessor: (row) => `${row.firstName} ${row.middleName} ${row.lastName}`,
      },
      {
        Header: 'Дата выхода',
        accessor: 'employmentDate',
      },
      {
        Header: 'Имя HR',
        accessor: 'hrName',
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
    ];

    let self = this;

    return (
      <div>
        <ReactTable
          data={this.state.employeeList}
          columns={columns}
          SubComponent={(row) => {
            return <EmployeePageShort data={row.original} />;
          }}
          getTdProps={(state, rowInfo, column, instance) => {
            return {
              onClick: (e, handleOriginal) => {
                if (
                  e.target.classList.contains('rt-expandable') ||
                  e.target.classList.contains('rt-expander')
                ) {
                  handleOriginal();
                } else {
                  self.props.history.push('/employee/' + rowInfo.row._original.id);
                }
              },
            };
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
    } = this.props.data;
    const hrName = this.props.data.hrName;
    const employmentDate = this.props.data.employmentDate;
    const workflow = this.props.data.workflow;

    return (
      <div className="workflow-horizontal">
        <p>{`${employeeFirstName} ${employeeLastName}`}</p>
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
