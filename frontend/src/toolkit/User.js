import PersonChooser from '../toolkit/PersonChooser';

import React from 'react';
import { Col, FormGroup, Label } from 'reactstrap';

class User extends React.Component {
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
          <PersonChooser
            id={this.props.id}
            persons={this.props.persons}
            personId={this.props.personId}
            onChange={this.personChange}
          />
        </Col>
      </FormGroup>
    );
  }
}

export default User;
