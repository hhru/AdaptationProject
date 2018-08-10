import React from 'react';
import { Col, FormGroup, FormText, Input, Label } from 'reactstrap';

class LabeledInputWithHelp extends React.Component {
  render() {
    return (
      <FormGroup row>
        <Label for={this.props.name} sm={4} lg={2}>
          {this.props.title}
        </Label>
        <Col sm={8} lg={10}>
          <Input
            type={this.props.type}
            name={this.props.name}
            id={this.props.name}
            value={this.props.value}
            onChange={this.props.onChange}
          />
          <FormText>{this.props.help}</FormText>
        </Col>
      </FormGroup>
    );
  }
}

export default LabeledInputWithHelp;
