import PersonChooser from '../toolkit/PersonChooser';

import React from 'react';
import { Row, Col, Button, Label, FormGroup, InputGroup, InputGroupAddon } from 'reactstrap';

class Person extends React.Component {
  personChange = (id) => {
    if (this.props.onChange instanceof Function) {
      this.props.onChange(id);
    }
  };

  render() {
    return (
      <div>
        <Row>
          <Label sm={4}>{this.props.title}</Label>
        </Row>
        <Row>
          <Col sm={8} lg={12}>
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
        </Row>
      </div>
    );
  }
}

export default Person;
