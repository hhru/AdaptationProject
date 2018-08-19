import GenderEdit from './GenderEdit';
import LabeledInput from './LabeledInput';
import LabeledInputWithHelp from './LabeledInputWithHelp';

import React from 'react';
import { Row, Col, FormGroup, Label, Input } from 'reactstrap';
import { AvField, AvRadioGroup, AvRadio } from 'availity-reactstrap-validation';
import { FEMALE, MALE } from '../constants/gender';

class PersonEdit extends React.Component {
  makePerson() {
    const person = {
      firstName: this.props.firstName,
      lastName: this.props.lastName,
      middleName: this.props.middleName,
      email: this.props.email,
      inside: this.props.inside,
      subdivision: this.props.subdivision,
    };
    return person;
  }

  handleInputChange = (event) => {
    const { name, value } = event.target;

    const person = this.makePerson();
    person[name] = value;

    if (this.props.onChange instanceof Function) {
      this.props.onChange(person);
    }
  };

  render() {
    const defaultValues = {};
    return (
      <div>
        <Row>
          <Col sm="4">
            <AvField
              name="lastName"
              label="Фамилия"
              value={this.props.lastName}
              onChange={this.handleInputChange}
              required
              errorMessage="Введите фамилию"
            />
          </Col>
          <Col sm="4">
            <AvField
              name="firstName"
              label="Имя"
              value={this.props.firstName}
              onChange={this.handleInputChange}
              required
              errorMessage="Введите имя"
            />
          </Col>
          <Col sm="4">
            <AvField
              name="middleName"
              label="Отчество"
              value={this.props.middleName}
              onChange={this.handleInputChange}
            />
          </Col>
        </Row>

        <Row>
          <Col sm="4">
            <AvRadioGroup name="genderRadioGroup" required errorMessage="Выберите пол">
              <AvRadio label="Мужской" value={MALE} />
              <AvRadio label="Женский" value={FEMALE} />
            </AvRadioGroup>
          </Col>
        </Row>

        <Row>
          <Col sm="6">
            <AvField
              name="email"
              label="Email"
              type="email"
              value={this.props.email}
              onChange={this.handleInputChange}
              required
              errorMessage="Введите валидный email"
            />
          </Col>
          <Col sm="6">
            <AvField
              name="inside"
              label="Инсайд"
              onChange={this.handleInputChange}
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
              onChange={this.handleInputChange}
              helpMessage="Подразделение, департамент, отдел, команда"
            />
          </Col>
        </Row>
      </div>
    );
  }
}

export default PersonEdit;
