import PersonChooser from '../toolkit/PersonChooser';

import React from 'react';
import { Col, Button, Label, FormGroup, InputGroup, InputGroupAddon } from 'reactstrap';

class Person extends React.Component {
  personChange = (id) => {
    if (this.props.onChange instanceof Function) {
      this.props.onChange(id);
    }
  };

  render() {
    return (
      <FormGroup row>
        <Label sm={4} lg={2}>
          {this.props.title}
        </Label>
        <Col sm={8} lg={10}>
          <InputGroup>
            <PersonChooser
              id={this.props.id}
              persons={this.props.persons}
              personId={this.props.personId}
              hasEmpty={this.props.hasEmpty}
              onChange={this.personChange}
            />
            <InputGroupAddon addonType="append">
              <Button onClick={this.props.onAdd}>{'Создать'}</Button>
            </InputGroupAddon>
          </InputGroup>
        </Col>
      </FormGroup>
    );
  }
}

export default Person;
