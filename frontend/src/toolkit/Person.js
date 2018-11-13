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
    if (this.props.required && this.props.onValid instanceof Function) {
      this.props.onValid(chiefId);
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
                name="personSelect"
                invalid={!this.props.chiefValid}
                onChange={this.personChange}
                value={this.props.personId ? this.props.personId : ''}
              >
                <option key="" value="" />
                {this.props.persons.map(({ id, firstName, lastName, email }) => (
                  <option key={id} value={id}>
                    {`${lastName} ${firstName} - ${email}`}
                  </option>
                ))}
              </Input>
              {!this.props.chiefValid && <FormFeedback>Выберите руководителя</FormFeedback>}
            </FormGroup>
          </Col>
          <Col sm={2} className="new-person">
            <Button onClick={this.props.onAdd}>Создать</Button>
          </Col>
        </Row>
      </div>
    );
  }
}

export default Person;
