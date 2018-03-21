import React from 'react';
import ReactDOM from 'react-dom';
import SortableTbl from 'react-sort-search-table';


class ListEmployees extends React.Component {
    constructor(props) {
        super(props);
        
        this.state = {
            employeeList: [],
        };

        this.handleEmployeeCallback = this.handleEmployeeCallback.bind(this);
        this.handleDeleteEmployee = this.handleDeleteEmployee.bind(this);
        this.handleEditEmployee = this.handleEditEmployee.bind(this);
    }

    handleEmployeeCallback(employeeList) {
        this.setState({
            employeeList: employeeList,
        })
    };

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
        })
    };

    handleEditEmployee(employeeId) {
        console.log('edit ' + employeeId);
    };

    componentDidMount () {
//        $.get('/api/employee/', this.handleEmployeeCallback);
        let employeeList = [
                {firstName: "Name1", middleName: "", lastName: "Lastname1", gender: "male", position: "frontend developer", email: "name1.Lastname1@hh.ru", employmentTimestamp: "2018-06-04", "id": 1, 'edit': this.handleEditEmployee, 'delete': this.handleDeleteEmployee},
                {firstName: "Olga", middleName: "M.", lastName: "Petrova", gender: "female", position: "backend developer", email: "olga.petrova@hh.ru", employmentTimestamp: "2018-06-04", "id": 2, 'edit': this.handleEditEmployee, 'delete': this.handleDeleteEmployee},
                {firstName: "Ivan", middleName: "I.", lastName: "Ivanov", gender: "male", position: "backend developer", email: "ivan.ivanov@hh.ru", employmentTimestamp: "2018-04-19", "id": 3, 'edit': this.handleEditEmployee, 'delete': this.handleDeleteEmployee}
        ];

        this.setState({
            employeeList: employeeList,
        })
    }

    render() {
        return (
            <div>
               <SortableTbl
                   tblData={this.state.employeeList}
                   tHead={['First Name', 'Last Name', 'Email', 'Employment Date', 'Edit', 'Delete']}
                   dKey={['firstName', 'lastName', 'email', 'employmentTimestamp', 'edit', 'delete']}
                   customTd={[
                        {custd: EditEmployee, keyItem: "edit"},
                        {custd: DeleteEmployee, keyItem: "delete"}
                       ]}
               />
           </div>
        )
    }
}


class EditEmployee extends React.Component{
    constructor(props) {
        super(props);
        this.editItem = this.editItem.bind(this);
    }

    editItem(){
        alert("edit " + this.props.rowData.firstName + " " + this.props.rowData.lastName);
        this.props.tdData(this.props.rowData['id']);
    }

    render () {
        return (	
            <td >	
                <input type="button" className="btn btn-warning" value="Edit" onClick={this.editItem}/>
            </td>
        );
    }
}


class DeleteEmployee extends React.Component{
    constructor(props) {
        super(props);
        this.deleteItem = this.deleteItem.bind(this);
    }

    deleteItem(){
        alert("delete " + this.props.rowData.firstName + " " + this.props.rowData.lastName);
        this.props.tdData(this.props.rowData['id']);
    }

    render () {
        return (	
            <td >	
                <input type="button" className="btn btn-danger" value="Delete" onClick={this.deleteItem}/>
            </td>
        );
    }
}


export default ListEmployees;
