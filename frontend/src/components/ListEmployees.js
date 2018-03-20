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
    }

    handleEmployeeCallback(employeeList) {
        this.setState({
            employeeList: employeeList,
        })
    };

    componentDidMount () {
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
            <div>
               <SortableTbl
                   tblData={this.state.employeeList}
                   tHead={['First Name', 'Last Name', 'email', 'employment date']}
                   dKey={['firstName', 'lastName', 'email', 'employmentTimestamp']}
               />
           </div>
        )
    }
}


export default ListEmployees;
