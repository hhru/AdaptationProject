import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';


class HomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoggedIn: false,
        };

        this.handleLoginLogoutButtonPressed = this.handleLoginLogoutButtonPressed.bind(this);
    }
  
    handleLoginLogoutButtonPressed () {
        let url = '/api/login/';
        let data = this.state;
//        axios.get(url)
//            .then(function (response) {
//                console.log(response);
                this.setState(
                    prevState => ({
                        isLoggedIn: !prevState.isLoggedIn
                    })
                );
//            })
//            .catch(function (error) {
//                console.log(error);
//            });
    }

    render() {
        return (
            <div>
                <p>
                    Welcome to adaptation homepage!
                </p>
                
                <button onClick={this.handleLoginLogoutButtonPressed}>
                    {this.state.isLoggedIn ? 'Logout' : 'Login'}
                </button>

                {this.state.isLoggedIn
                    ? <HomePageForLogedInUser history={this.props.history} />
                    : null
                }
 
            </div>
        );
    }
}



class HomePageForLogedInUser extends React.Component {
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


export default HomePage;
