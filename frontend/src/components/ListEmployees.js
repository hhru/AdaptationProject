import React from 'react';
import ReactDOM from 'react-dom';
import { Progress } from 'reactstrap';
import ReactTable from 'react-table';

import 'react-table/react-table.css';
import '!style-loader!css-loader!bootstrap/dist/css/bootstrap.css';

class ListEmployees extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      employeeList: [],
      loaded: false,
    };

    this.handleGetBriefEmployeeList = this.handleGetBriefEmployeeList.bind(
      this
    );
  }

  handleGetBriefEmployeeList(employeeList) {
    for (let i = 0; i < employeeList.length; i++) {
      employeeList[i]['currentWorkflowStep'] = employeeList[i].workflow[0];
      employeeList[i]['workflowToBeShown'] = [];
      for (let j = 0; j < employeeList[i].workflow.length; j++) {
        let color;
        if (employeeList[i].workflow[j]['status'] == 'COMPLETE') {
          employeeList[i]['currentWorkflowStep'] = employeeList[i].workflow[j];
          color = 'success';
        } else if (employeeList[i].workflow[j]['status'] == 'INCOMPLETE') {
          color = 'light';
        } else if (employeeList[i].workflow[j]['status'] == 'OVERDUE') {
          color = 'danger';
        } else if (employeeList[i].workflow[j]['status'] == 'IN_PROCESS') {
          employeeList[i]['currentWorkflowStep'] = employeeList[i].workflow[j];
          color = 'warning';
        } else {
          color = 'light';
        }
        employeeList[i]['workflowToBeShown'].push({
          color: color,
          key: i + ' ' + j,
          value: 100.0 / employeeList[i].workflow.length,
          textToDisplay: j + 1,
        });
      }
    }
    this.setState({
      employeeList: employeeList,
      loaded: true,
    });
  }

  componentDidMount() {
    let response = [
      {
        id: 1,
        firstName: 'Иван',
        lastName: 'Иванов',
        middleName: 'Иванович',
        hrName: 'Бежецкова Е.',
        employmentTimestamp: '2017-07-10 16:30:00',
        workflow: [
          {
            step: 1,
            name: 'Сотрудник добавлен в систему',
            status: 'COMPLETE',
          },
          {
            step: 2,
            name: 'Сотрудник добавлен в систему',
            status: 'COMPLETE',
          },
          {
            step: 3,
            name: 'Ставятся задачи на испытательный срок',
            status: 'OVERDUE',
          },
          {
            step: 4,
            name: 'шаг 4',
            status: 'IN_PROCESS',
          },
          {
            step: 5,
            name: 'шаг 5',
            status: 'INCOMPLETE',
          },
          {
            step: 6,
            name: 'шаг 6',
            status: 'INCOMPLETE',
          },
          {
            step: 7,
            name: 'шаг 7',
            status: 'INCOMPLETE',
          },
          {
            step: 8,
            name: 'шаг 8',
            status: 'INCOMPLETE',
          },
          {
            step: 9,
            name: 'шаг 9',
            status: 'INCOMPLETE',
          },
          {
            step: 10,
            name: 'Успешное прохождение испытательного срока',
            status: 'INCOMPLETE',
          },
        ],
      },
      {
        id: 2,
        firstName: 'Ольга',
        lastName: 'Петрова',
        middleName: 'Андреевна',
        hrName: 'Бежецкова Е.',
        employmentTimestamp: '2017-11-10 16:30:00',
        workflow: [
          {
            step: 1,
            name: 'Сотрудник добавлен в систему',
            status: 'COMPLETE',
          },
          {
            step: 2,
            name: 'Сотрудник добавлен в систему',
            status: 'INCOMPLETE',
          },
          {
            step: 3,
            name: 'Ставятся задачи на испытательный срок',
            status: 'INCOMPLETE',
          },
          {
            step: 4,
            name: 'шаг 4',
            status: 'INCOMPLETE',
          },
          {
            step: 5,
            name: 'шаг 5',
            status: 'INCOMPLETE',
          },
          {
            step: 6,
            name: 'шаг 6',
            status: 'INCOMPLETE',
          },
          {
            step: 7,
            name: 'шаг 7',
            status: 'INCOMPLETE',
          },
          {
            step: 8,
            name: 'шаг 8',
            status: 'INCOMPLETE',
          },
          {
            step: 9,
            name: 'шаг 9',
            status: 'INCOMPLETE',
          },
          {
            step: 10,
            name: 'Успешное прохождение испытательного срока',
            status: 'INCOMPLETE',
          },
        ],
      },
    ];

    let url = '/api/employee/all/brief/';
    //axios.get(url)
    //    .then(function (response) {
    //        console.log(response);
    this.handleGetBriefEmployeeList(response);
    //    })
    //    .catch(function (error) {
    //        console.log(error);
    //    });
  }

  render() {
    let columns = [
      {
        Header: 'ФИО',
        id: 'fullName',
        accessor: (row) =>
          row.firstName + ' ' + row.middleName + ' ' + row.lastName,
      },
      {
        Header: 'Дата выхода',
        accessor: 'employmentTimestamp',
      },
      {
        Header: 'Состояние',
        accessor: 'progress',
        Cell: (row) => (
          <div>
            <div className="text-center">
              {row.original.currentWorkflowStep['name']}
            </div>

            <Progress multi>
              {row.original.workflowToBeShown.map((workflowStage) => (
                <Progress
                  bar
                  color={workflowStage.color}
                  value={workflowStage.value}
                  key={workflowStage.key}
                >
                  {workflowStage.textToDisplay}
                </Progress>
              ))}
            </Progress>
          </div>
        ),
      },
    ];

    return (
      <div>
        <ReactTable data={this.state.employeeList} columns={columns} />
      </div>
    );
  }
}

export default ListEmployees;
