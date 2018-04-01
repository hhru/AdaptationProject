import React from 'react';
import ReactDOM from 'react-dom';
import SortableTbl from 'react-sort-search-table';
import { Progress } from 'reactstrap';

class ListEmployees extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      employeeList: [],
    };

    this.handleGetBriefEmployeeList = this.handleGetBriefEmployeeList.bind(
      this
    );
    this.handleDeleteEmployee = this.handleDeleteEmployee.bind(this);
    this.handleEditEmployee = this.handleEditEmployee.bind(this);
  }

  handleGetBriefEmployeeList(employeeList) {
    for (let i = 0; i < employeeList.length; i++) {
      employeeList[i]['edit'] = this.handleEditEmployee;
      employeeList[i]['delete'] = this.handleDeleteEmployee;
      employeeList[i]['currentWorkflowStep'] = employeeList[i].workflow[0];
      employeeList[i]['workflowToBeShown'] = [];
      for (let j = 0; j < employeeList[i].workflow.length; j++) {
        let color;
        if (employeeList[i].workflow[j]['status'] == 'COMPLETE') {
          employeeList[i]['currentWorkflowStep'] = employeeList[i].workflow[j];
          color = 'success';
        } else if (employeeList[i].workflow[j]['status'] == 'INCOMPLETE') {
          color = 'info';
        } else if (employeeList[i].workflow[j]['status'] == 'OVERDUE') {
          color = 'danger';
        } else if (employeeList[i].workflow[j]['status'] == 'IN_PROCESS') {
          employeeList[i]['currentWorkflowStep'] = employeeList[i].workflow[j];
          color = 'warning';
        } else {
          color = 'grey';
        }
        employeeList[i]['workflowToBeShown'].push({
          color: color,
          value: 100.0 / employeeList[i].workflow.length,
          textToDisplay: j + 1,
        });
      }
    }
    this.setState({
      employeeList: employeeList,
    });
  }

  handleDeleteEmployee(employeeId) {
    //        TODO: notify backend!
    let newEmployeeList = [];
    for (let i = 0; i < this.state.employeeList.length; i++) {
      if (this.state.employeeList[i]['id'] != employeeId) {
        newEmployeeList.push(this.state.employeeList[i]);
      }
    }

    this.setState({
      employeeList: newEmployeeList,
    });
  }

  handleEditEmployee(employeeId) {
    console.log('edit ' + employeeId);
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
    //        axios.get(url)
    //            .then(function (response) {
    //                console.log(response);
    this.handleGetBriefEmployeeList(response);
    //            })
    //            .catch(function (error) {
    //                console.log(error);
    //            });
  }

  render() {
    return (
      <div>
        <SortableTbl
          tblData={this.state.employeeList}
          tHead={[
            'First Name',
            'Last Name',
            'Hr Name',
            'Employment Date',
            'Workflow',
            'Edit',
            'Delete',
          ]}
          dKey={[
            'firstName',
            'lastName',
            'hrName',
            'employmentTimestamp',
            'workflowToBeShown',
            'edit',
            'delete',
          ]}
          customTd={[
            { custd: EditEmployee, keyItem: 'edit' },
            { custd: DeleteEmployee, keyItem: 'delete' },
            { custd: ShowEmployeeProgress, keyItem: 'workflowToBeShown' },
          ]}
          defaultCSS={true}
        />
      </div>
    );
  }
}

class EditEmployee extends React.Component {
  constructor(props) {
    super(props);
    this.editItem = this.editItem.bind(this);
  }

  editItem() {
    alert(
      'edit ' + this.props.rowData.firstName + ' ' + this.props.rowData.lastName
    );
    this.props.tdData(this.props.rowData['id']);
  }

  render() {
    return (
      <td>
        <input
          type="button"
          className="btn btn-warning"
          value="Edit"
          onClick={this.editItem}
        />
      </td>
    );
  }
}

class DeleteEmployee extends React.Component {
  constructor(props) {
    super(props);
    this.deleteItem = this.deleteItem.bind(this);
  }

  deleteItem() {
    alert(
      'delete ' +
        this.props.rowData.firstName +
        ' ' +
        this.props.rowData.lastName
    );
    this.props.tdData(this.props.rowData['id']);
  }

  render() {
    return (
      <td>
        <input
          type="button"
          className="btn btn-danger"
          value="Delete"
          onClick={this.deleteItem}
        />
      </td>
    );
  }
}

class ShowEmployeeProgress extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <td style={{ width: '250px', minWidth: '250px' }}>
        <div className="text-center">
          {this.props.rowData.currentWorkflowStep['name']}
        </div>
        <Progress multi>
          {this.props.rowData.workflowToBeShown.map((workflowStage) => (
            <Progress
              bar
              color={workflowStage.color}
              value={workflowStage.value}
            >
              {workflowStage.textToDisplay}
            </Progress>
          ))}
        </Progress>
      </td>
    );
  }
}

export default ListEmployees;
