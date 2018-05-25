import GenderEdit from '../toolkit/GenderEdit';
import PersonEdit from '../toolkit/PersonEdit';
import LabeledInput from '../toolkit/LabeledInput';

import React from 'react';

class Employee extends React.Component {
  makeEmployee() {
    return {
      firstName: this.props.firstName,
      lastName: this.props.lastName,
      middleName: this.props.middleName,
      email: this.props.email,
      inside: this.props.inside,
      gender: this.props.gender,
      position: this.props.position,
      employmentDate: this.props.employmentDate
    };
  }

  handlePersonChange = person => {
    let employee = this.makeEmployee();
    employee = {
      ...employee,
      ...person
    };
    this.props.onChange(employee);
  }

  handlePositionChange = event => {
    const employee = this.makeEmployee();
    employee.position = event.target.value;
    this.props.onChange(employee);
  }

  handleEmploymentDateChange = event => {
    const employee = this.makeEmployee();
    employee.employmentDate = event.target.value;
    this.props.onChange(employee);
  }

  handleGenderChange= gender => {
    const employee = this.makeEmployee();
    employee.gender = gender;
    this.props.onChange(employee);
  }

  render() {
    return (
      <div>
        <PersonEdit
          title="Сотрудник"
          firstName={this.props.firstName}
          lastName={this.props.lastName}
          middleName={this.props.middleName}
          email={this.props.email}
          inside={this.props.inside}
          onChange={this.handlePersonChange}
        />
        <GenderEdit
          gender={this.props.gender}
          onChange={this.handleGenderChange}
        />
        <LabeledInput
          title="Должность"
          name="position"
          type="text"
          isRequired={false}
          value={this.props.position}
          onChange={this.handlePositionChange}
        />
        <LabeledInput
          title="Дата выхода"
          name="employmentDate"
          type="date"
          isRequired={true}
          value={this.props.employmentDate}
          onChange={this.handleEmploymentDateChange}
        />
      </div>
    );
  }
}


export default Employee;
