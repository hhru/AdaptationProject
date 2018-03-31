import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import FaAdjust from 'react-icons/lib/fa/adjust';
import FaCheckCircle from 'react-icons/lib/fa/check-circle';
import FaCircleO from 'react-icons/lib/fa/circle-o';
import FaClockO from 'react-icons/lib/fa/clock-o';
import FaCircle from 'react-icons/lib/fa/circle';
import FaExclamationCircle from 'react-icons/lib/fa/exclamation-circle';
import FaPlusCircle from 'react-icons/lib/fa/plus-circle';
import FaQuestionCircle from 'react-icons/lib/fa/question-circle';
import FaSmileO from 'react-icons/lib/fa/smile-o';
import FaTimesCircle from 'react-icons/lib/fa/times-circle';
import FaEdit from 'react-icons/lib/fa/edit';


class EmployeePage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            employeeId: this.props.location.search,
            data: {},
        };

        this.handleGetEmployeeInfo = this.handleGetEmployeeInfo.bind(this);
    }

    handleGetEmployeeInfo (response) {
        let responseData = {
          "id": 1,
          "self": {
            "id": 3,
            "firstName": "Джон",
            "lastName": "МакКлейн",
            "middleName": "Иванович",
            "email": "die@hard.com",
            "inside": "john"
          },
          "chief": {
            "id": 1,
            "firstName": "Гусев",
            "lastName": "Леонид",
            "middleName": "Викторович",
            "email": "l.gusev@hh.ru",
            "inside": "gusev"
          },
          "mentor": {
            "id": 1,
            "firstName": "Гусев",
            "lastName": "Леонид",
            "middleName": "Викторович",
            "email": "l.gusev@hh.ru",
            "inside": "gusev"
          },
          "hr": {
            "id": 1,
            "firstName": "Билл",
            "lastName": "Гейтс",
            "middleName": null,
            "email": "gates@microsoft.com",
            "inside": "gates"
          },
          "employmentTimestamp": "2018-03-31 08:53:46",
          "workflow": [
            {
              "id": 8,
              "type": "ADD",
              "status": "CURRENT",
              "deadlineTimestamp": null,
              "comment": null
            },
            {
              "id": 7,
              "type": "TASK_LIST",
              "status": "NOT_DONE",
              "deadlineTimestamp": null,
              "comment": null
            },
            {
              "id": 6,
              "type": "WELCOME_MEETING",
              "status": "NOT_DONE",
              "deadlineTimestamp": null,
              "comment": null
            },
            {
              "id": 5,
              "type": "INTERIM_MEETING",
              "status": "NOT_DONE",
              "deadlineTimestamp": null,
              "comment": null
            },
            {
              "id": 4,
              "type": "INTERIM_MEETING_RESULT",
              "status": "NOT_DONE",
              "deadlineTimestamp": null,
              "comment": null
            },
            {
              "id": 3,
              "type": "FINAL_MEETING",
              "status": "NOT_DONE",
              "deadlineTimestamp": null,
              "comment": null
            },
            {
              "id": 2,
              "type": "FINAL_MEETING_RESULT",
              "status": "NOT_DONE",
              "deadlineTimestamp": null,
              "comment": null
            },
            {
              "id": 1,
              "type": "QUESTIONNAIRE",
              "status": "NOT_DONE",
              "deadlineTimestamp": null,
              "comment": null
            }
          ]
        }
        this.setState({
            data: responseData,
        })
    }

    componentWillMount () {
        let response;
//        $.get('/api/employee/' + sthis.state.employeeId, this.handleGetEmployeeInfo);
        this.handleGetEmployeeInfo(response);
    }

    render() {
        return (
            <div>
                <p>
                    {this.state.data.self.firstName + ' ' + this.state.data.self.middleName + ' ' + this.state.data.self.lastName}
                </p>
                <p>
                    {this.state.data.self.email}
                </p>
                <p>
                    {'Начальник: ' + this.state.data.chief.firstName + ' ' + this.state.data.chief.middleName + ' ' + this.state.data.chief.lastName}
                </p>
                <p>
                    {'Ментор: ' + this.state.data.mentor.firstName + ' ' + this.state.data.mentor.middleName + ' ' + this.state.data.mentor.lastName}
                </p>
                <p>
                    {'HR: ' + this.state.data.hr.firstName + ' ' + this.state.data.hr.middleName + ' ' + this.state.data.hr.lastName}
                </p>
                <p>
                    {'Дата выхода: ' + this.state.data.employmentTimestamp}
                </p>
                <FaAdjust/>
                <FaCheckCircle/>
                <FaCircleO/>
                <FaClockO/>
                <FaCircle/>
                <FaExclamationCircle/>
                <FaPlusCircle/>
                <FaQuestionCircle/>
                <FaSmileO/>
                <FaTimesCircle/>
                <FaEdit/>
            </div>
        );
    }
}


export default EmployeePage;
