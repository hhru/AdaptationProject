import React from 'react';
import ReactDOM from 'react-dom';


class LogedInHome extends React.Component {
    constructor(props) {
        super(props);

        this.handleAddEmployee = this.handleAddEmployee.bind(this);
        this.handleListEmployees = this.handleListEmployees.bind(this);
    }
  
    handleAddEmployee () {
        this.props.history.push('/add_employee');
    }

    handleListEmployees () {
        this.props.history.push('/list_employees');
    }

    render() {
        return (
            <div>
                <button onClick={this.handleAddEmployee}>
                    Add Employee
                </button>

                <button onClick={this.handleListEmployees}>
                    List Employees
                </button>

            </div>
        );
    }
}


export default LogedInHome;

