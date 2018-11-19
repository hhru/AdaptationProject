import GenderEdit from './GenderEdit';

import React from 'react';
import { Row, Col } from 'reactstrap';
import { AvField } from 'availity-reactstrap-validation';

class PersonEdit extends React.Component {
  makePerson() {
    return {
      firstName: this.props.firstName,
      lastName: this.props.lastName,
      middleName: this.props.middleName,
      email: this.props.email,
      inside: this.props.inside,
      subdivision: this.props.subdivision,
    }
  }

  inputChange = (event) => {
    const { name, value } = event.target;

    const person = this.makePerson();
    person[name] = value;

    if (this.props.onChange instanceof Function) {
      this.props.onChange(person);
    }
  };

  render() {
    return (
      <div>
        <Row>
          <Col sm="4">
            <AvField
              name="lastName"
              label="Фамилия"
              value={this.props.lastName}
              onChange={this.inputChange}
              required
              errorMessage="Введите фамилию"
            />
          </Col>
          <Col sm="4">
            <AvField
              name="firstName"
              label="Имя"
              value={this.props.firstName}
              onChange={this.inputChange}
              required
              errorMessage="Введите имя"
            />
          </Col>
          <Col sm="4">
            <AvField
              name="middleName"
              label="Отчество"
              value={this.props.middleName}
              onChange={this.inputChange}
            />
          </Col>
        </Row>

        <Row>
          <Col sm="4">
            <GenderEdit gender={this.props.gender} onChange={this.props.onGenderChange} />
          </Col>
        </Row>

        <Row>
          <Col sm="6">
            <AvField
              name="email"
              label="Email"
              type="email"
              value={this.props.email}
              onChange={this.inputChange}
              required
              errorMessage="Введите валидный email"
            />
          </Col>
          <Col sm="6">
            <AvField
              name="inside"
              label="Инсайд"
              onChange={this.inputChange}
              value={this.props.inside}
            />
          </Col>
        </Row>

        <Row>
          <Col sm="12">
            <AvField
              name="subdivision"
              label="Подразделение"
              value={this.props.subdivision}
              onChange={this.inputChange}
              helpMessage="Подразделение, департамент, отдел, команда"
            />
          </Col>
        </Row>
      </div>
    );
  }
}

export default PersonEdit;
