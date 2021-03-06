import { FEMALE, MALE } from '../constants/gender';

import React from 'react';
import { Col, FormGroup, Label, Input } from 'reactstrap';

class GenderEdit extends React.Component {
  genderChange = (changeEvent) => {
    const gender = changeEvent.target.value;
    this.props.onChange(gender);
  };

  render() {
    return (
      <FormGroup row>
        <Label sm={4} lg={2}>
          {'Пол'}
        </Label>
        <Col sm={8} lg={10}>
          <FormGroup check>
            <Label check>
              <Input
                type="radio"
                id="male"
                name="genderRadio"
                value={MALE}
                checked={this.props.gender === MALE}
                onChange={this.genderChange}
              />{' '}
              {'Мужской'}
            </Label>
          </FormGroup>
          <FormGroup check>
            <Label check>
              <Input
                type="radio"
                id="female"
                name="genderRadio"
                value={FEMALE}
                checked={this.props.gender === FEMALE}
                onChange={this.genderChange}
              />{' '}
              {'Женский'}
            </Label>
          </FormGroup>
        </Col>
      </FormGroup>
    );
  }
}

export default GenderEdit;
