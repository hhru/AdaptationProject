import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import { Progress } from 'reactstrap';
import ReactTable from 'react-table';

import '!style-loader!css-loader!react-table/react-table.css';
import '!style-loader!css-loader!bootstrap/dist/css/bootstrap.css';

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
      COMPLETE_OVERDUE: 'danger',
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
                if (!e.target.classList.contains('rt-expandable')) {
                  self.props.history.push('/employee/' + rowInfo.row._original.id);
                } else {
                  handleOriginal();
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
    console.log(this.props.data);
    return (
      <div style={{ padding: '20px' }}>
        <em>You can put any component you want here!</em>
      </div>
    );
  }
}

export default ListEmployees;
