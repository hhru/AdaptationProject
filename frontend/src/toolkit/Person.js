import React from 'react';
import {
  Row,
  Col,
  Button,
  Label,
  FormGroup,
  InputGroup,
  InputGroupAddon,
  Input,
  FormFeedback,
} from 'reactstrap';

class Person extends React.Component {
  state = {
    valid: true,
  };

  personChange = (event) => {
    const chiefId = event.target.value;
    if (this.props.required) {
      this.setState({ valid: !chiefId ? false : true });
    }
    if (this.props.onChange instanceof Function) {
      this.props.onChange(chiefId);
    }
  };

  render() {
    return (
      <div>
        <Row>
          <Col sm={10}>
            <FormGroup>
              <Label>{this.props.title}</Label>
              <Input
                type="select"
                name="select"
                invalid={!this.state.valid}
                onChange={this.personChange}
                value={this.props.personId ? this.props.personId : ''}
              >
                <option key={''} value={''} />
                {this.props.persons.map((person) => (
                  <option key={person.id} value={person.id}>
                    {`${person.firstName} ${person.lastName} - ${person.email}`}
                  </option>
                ))}
              </Input>
              {!this.state.valid && <FormFeedback>Выберите руководителя</FormFeedback>}
            </FormGroup>
          </Col>
          <Col sm={2} className="new-person">
            <Button onClick={this.props.onAdd}>{'Создать'}</Button>
          </Col>
        </Row>
      </div>
    );
  }
}

export default Person;
