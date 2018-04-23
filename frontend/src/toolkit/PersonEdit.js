import GenderEdit from './GenderEdit';
import LabeledInput from './LabeledInput';

import React from 'react';
import { Col, FormGroup, Label, Input } from 'reactstrap';

class PersonEdit extends React.Component {
  makePerson() {
    const person = {
      firstName: this.props.firstName,
      lastName: this.props.lastName,
      middleName: this.props.middleName,
      email: this.props.email,
      inside: this.props.inside
    }
    return person;
  }

  handleInputChange = event => {
    const {name, value} = event.target;

    const person = this.makePerson();
    person[name] = value;

    if (this.props.onChange instanceof Function) {
      this.props.onChange(person);
    }
  };

  render() {
    return (
      <div>
        <LabeledInput
          title="Фамилия"
          name="lastName"
          type="text"
          value={this.props.lastName}
          onChange={this.handleInputChange}
        />
        <LabeledInput
          title="Имя"
          name="firstName"
          type="text"
          value={this.props.firstName}
          onChange={this.handleInputChange}
        />
        <LabeledInput
          title="Отчество"
          name="middleName"
          type="text"
          value={this.props.middleName}
          onChange={this.handleInputChange}
        />
        <LabeledInput
          title="Email"
          name="email"
          type="email"
          value={this.props.email}
          onChange={this.handleInputChange}
        />
        <LabeledInput
          title="Инсайд"
          name="inside"
          type="inside"
          value={this.props.inside}
          onChange={this.handleInputChange}
        />
      </div>
    );
  }
}

export default PersonEdit;
