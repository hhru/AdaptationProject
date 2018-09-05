import GenderEdit from '../toolkit/GenderEdit';
import PersonEdit from '../toolkit/PersonEdit';
import LabeledInput from '../toolkit/LabeledInput';
import { Row, Col } from 'reactstrap';
import { AvField } from 'availity-reactstrap-validation';

import React from 'react';

class Employee extends React.Component {
  makeEmployee() {
    return {
      firstName: this.props.firstName,
      lastName: this.props.lastName,
      middleName: this.props.middleName,
      email: this.props.email,
      inside: this.props.inside,
      subdivision: this.props.subdivision,
      gender: this.props.gender,
      position: this.props.position,
      employmentDate: this.props.employmentDate,
      interimDate: this.props.interimDate,
      finalDate: this.props.finalDate,
    };
  }

  handlePersonChange = (person) => {
    let employee = this.makeEmployee();
    employee = {
      ...employee,
      ...person,
    };
    this.props.onChange(employee);
  };

  handlePositionChange = (event) => {
    const employee = this.makeEmployee();
    employee.position = event.target.value;
    this.props.onChange(employee);
  };

  handleEmploymentDateChange = (event) => {
    const employee = this.makeEmployee();
    employee.employmentDate = event.target.value;

    var bufDate = employee.employmentDate.split('-');
    bufDate = new Date(bufDate[0], parseInt(bufDate[1]) - 1, bufDate[2]);
    bufDate.setMonth(bufDate.getMonth() + 1);
    bufDate.setDate(bufDate.getDate() + 15);
    var month =
      bufDate.getMonth() + 1 < 10 ? '0' + (bufDate.getMonth() + 1) : bufDate.getMonth() + 1;
    var day = bufDate.getDate() < 10 ? '0' + bufDate.getDate() : bufDate.getDate();
    employee.interimDate = bufDate.getFullYear() + '-' + month + '-' + day;

    bufDate.setMonth(bufDate.getMonth() + 1);
    bufDate.setDate(bufDate.getDate() + 15);
    month = bufDate.getMonth() + 1 < 10 ? '0' + (bufDate.getMonth() + 1) : bufDate.getMonth() + 1;
    day = bufDate.getDate() < 10 ? '0' + bufDate.getDate() : bufDate.getDate();
    employee.finalDate = bufDate.getFullYear() + '-' + month + '-' + day;

    this.props.onChange(employee);
  };

  handleInterimDateChange = (event) => {
    const employee = this.makeEmployee();
    employee.interimDate = event.target.value;
    this.props.onChange(employee);
  };

  handleFinalDateChange = (event) => {
    const employee = this.makeEmployee();
    employee.finalDate = event.target.value;
    this.props.onChange(employee);
  };

  handleGenderChange = (gender) => {
    const employee = this.makeEmployee();
    employee.gender = gender;
    this.props.onChange(employee);
  };

  render() {
    return (
      <div>
        <PersonEdit
          title="Сотрудник"
          firstName={this.props.firstName}
          lastName={this.props.lastName}
          middleName={this.props.middleName}
          email={this.props.email}
          gender={this.props.gender}
          inside={this.props.inside}
          subdivision={this.props.subdivision}
          onChange={this.handlePersonChange}
          onGenderChange={this.handleGenderChange}
        />
        <Row>
          <Col sm="12">
            <AvField
              name="position"
              label="Должность"
              value={this.props.position}
              onChange={this.handlePositionChange}
              required
              errorMessage="Введите должность"
            />
          </Col>
        </Row>
        <Row>
          <Col sm="4">
            <AvField
              name="employmentDate"
              label="Дата выхода"
              type="date"
              value={this.props.employmentDate}
              onChange={this.handleEmploymentDateChange}
              required
              errorMessage="Выберите дату выхода"
            />
          </Col>
          <Col sm="4">
            <AvField
              name="interimDate"
              label="Промежуточная дата ИС"
              type="date"
              value={this.props.interimDate}
              onChange={this.handleInterimDateChange}
              required
              errorMessage="Выберите дату выхода"
            />
          </Col>
          <Col sm="4">
            <AvField
              name="finalDate"
              label="Дата окончания ИС"
              type="date"
              value={this.props.finalDate}
              onChange={this.handleFinalDateChange}
              required
              errorMessage="Выберите дату выхода"
            />
          </Col>
        </Row>
      </div>
    );
  }
}

export default Employee;
