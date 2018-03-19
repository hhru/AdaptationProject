import React from 'react';
import ReactDOM from 'react-dom';
import ReactTable from 'react-table';

import 'react-table/react-table.css';


class ListEmployees extends React.Component {
    constructor(props) {
        super(props);
        
        this.state = {
            employeeList: [],
            columns: [{Header: 'First Name', accessor: 'firstName'}, {Header: 'Last Name', accessor: 'lastName'}, {Header: 'email', accessor: 'email'}, {Header: 'employment date', accessor: 'employmentTimestamp'}],
        };

        this.handleEmployeeCallback = this.handleEmployeeCallback.bind(this);
    }

    handleEmployeeCallback(employeeList) {
        this.setState({
            employeeList: employeeList,
        })
    };

    componentWillMount () {
//        $.get('/api/employee/', this.handleEmployeeCallback);
        this.setState({
            employeeList: [
                {firstName: "Name1", middleName: "", lastName: "Lastname1", gender: "male", position: "frontend developer", email: "name1.Lastname1@hh.ru", employmentTimestamp: "2018-06-04"},
                {firstName: "Name2", middleName: "M.", lastName: "Lastname2", gender: "female", position: "backend developer", email: "name2.Lastname2@hh.ru", employmentTimestamp: "2018-06-04"}
            ]
        })
    }

    render() {
        return (
            <ReactTable
                data={this.state.employeeList}
                columns={this.state.columns}
            />
        )
    }
}


export default ListEmployees;
