import PersonChooser from '../toolkit/PersonChooser';

import React from 'react';
import { Row, Col, FormGroup, Label } from 'reactstrap';

class User extends React.Component {
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
          <Col sm={12} lg={12}>
            <PersonChooser
              id={this.props.id}
              persons={this.props.persons}
              personId={this.props.personId}
              onChange={this.personChange}
            />
          </Col>
        </Row>
      </div>
    );
  }
}

export default User;
