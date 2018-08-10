import React from 'react';
import { Col, FormGroup, Label, Input } from 'reactstrap';

class LabeledInput extends React.Component {
  render() {
    return (
      <FormGroup row>
        <Label for={this.props.name} sm={4}>{this.props.title}</Label>
        <Col sm={8}>
          <Input
            type={this.props.type}
            name={this.props.name}
            id={this.props.name}
            value={this.props.value}
            onChange={this.props.onChange}
          />
        </Col>
      </FormGroup>
    );
  }
}

export default LabeledInput;
