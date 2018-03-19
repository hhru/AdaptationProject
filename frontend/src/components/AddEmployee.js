import React from 'react';
import ReactDOM from 'react-dom';
import $ from 'jquery'; 

class AddEmployee extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            firstName: '',
            middleName: '',
            lastName: '',
            gender: '',
            position: '',
            email: '',
            employmentTimestamp: '',
        };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });
    }

    handleSubmit(event) {
        var url = '/api/employee/';
        var data = this.state;
//        $.post(url, data, function (response) {
//            console.log(response);
//        }.bind(this));
console.log(data);
        alert('Employee ' + this.state.firstName + ' '  + this.state.lastName + ' was added');
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    First Name:
                    <input
                        name="firstName"
                        type="text"
                        value={this.state.firstName}
                        onChange={this.handleInputChange} 
                    />
                </label>

                <br />
                <label>
                    Middle Name:
                    <input
                        name="middleName"
                        type="text"
                        value={this.state.middleName}
                        onChange={this.handleInputChange} 
                    />
                </label>

                <br />
                <label>
                    Last Name:
                    <input
                        name="lastName"
                        type="text"
                        value={this.state.lastName}
                        onChange={this.handleInputChange} 
                    />
                </label>

                <br />
                <label>
                    Gender:
                    <input
                        name="gender"
                        type="radio"
                        value='male'
                        onChange={this.handleInputChange} 
                    /> Male
                    <input
                        name="gender"
                        type="radio"
                        value='female'
                        onChange={this.handleInputChange} 
                    /> Female
                </label>

                <br />
                <label>
                    Position:
                    <input
                        name="position"
                        type="text"
                        value={this.state.position}
                        onChange={this.handleInputChange} 
                    />
                </label>

                <br />
                <label>
                    Email:
                    <input
                        name="email"
                        type="email"
                        value={this.state.email}
                        onChange={this.handleInputChange} 
                    />
                </label>

                <br />
                <label>
                    Date of employment:
                    <input
                        name="employmentTimestamp"
                        type="date"
                        value={this.state.employmentTimestamp}
                        onChange={this.handleInputChange} 
                    />
                </label>
                <br />

                <input type="submit" value="Submit" />
            </form>
        );
    }
}


export default AddEmployee;
