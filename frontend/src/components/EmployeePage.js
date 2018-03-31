import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';


class EmployeePage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            employeeId: this.props.location.search,
            personalInfo: {
                firstName: '',
                middleName: '',
                lastName: '',
                gender: '',
                position: '',
                email: '',
                employmentTimestamp: '',
                insideUrl: '',
            },
            managerEmail: '',
            hrEmail: '',
            modificationLog: '',
            tasksForAdaptationPeriod: [],
            personalComments: '',
            workflow: {
                step1: {
                    status: '',
                    isMandatory: '',
                    description: '',
                    comments: '',
                    additionalInfo: ''
                }
            },
        };

        this.handleGetEmployeeInfo = this.handleGetEmployeeInfo.bind(this);
    }

    handleGetEmployeeInfo (response) {
    }

    componentDidMount () {
//        $.get('/api/employee/' + sthis.state.employeeId, this.handleGetEmployeeInfo);
        let employeeInfo = {firstName: "Olga", middleName: "M.", lastName: "Petrova", gender: "female", position: "backend developer", email: "olga.petrova@hh.ru", employmentTimestamp: "2018-06-04", "id": 2, pipeline: {1: true, 2: false, 3: true, 4: true, 5: true}}
        this.setState({
            employeeInfo
        })
    }

    render() {
        return (
            <div>
                <p>
                    Some text
                </p>
            </div>
        );
    }
}


export default EmployeePage;
