import React from 'react';
import { Row, Col, Form, Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import PersonEdit from './PersonEdit';
import { AvForm, AvField } from 'availity-reactstrap-validation';

class PersonCreator extends React.Component {
  state = {
    firstName: '',
    lastName: '',
    middleName: '',
    email: '',
    inside: '',
    subdivision: '',
  };

  handlePersonCreate = () => {
    this.props.onCreate({
      firstName: this.state.firstName,
      lastName: this.state.lastName,
      middleName: this.state.middleName,
      email: this.state.email,
      inside: this.state.inside,
      subdivision: this.state.subdivision,
    });

    // clear data
    this.setState({
      firstName: '',
      lastName: '',
      middleName: '',
      email: '',
      inside: '',
      subdivision: '',
    });
  };

  makePerson() {
    const person = {
      firstName: this.state.firstName,
      lastName: this.state.lastName,
      middleName: this.state.middleName,
      email: this.state.email,
      inside: this.state.inside,
      subdivision: this.state.subdivision,
    };
    return person;
  }

  handleInputChange = (event) => {
    const { name, value } = event.target;

    const person = this.makePerson();
    person[name] = value;

    this.setState(person);
  };

  render() {
    return (
      <Modal isOpen={this.props.modal} toggle={this.props.toggle}>
        <ModalHeader toggle={this.props.toggle}>{this.props.title}</ModalHeader>
        <ModalBody>
          <AvForm onValidSubmit={this.handlePersonCreate} onInvalidSubmit={() => {}}>
            <AvField
              name="lastName"
              label="Фамилия"
              onChange={this.handleInputChange}
              required
              errorMessage="Введите фамилию"
            />
            <AvField
              name="firstName"
              label="Имя"
              onChange={this.handleInputChange}
              required
              errorMessage="Введите имя"
            />
            <AvField name="middleName" label="Отчество" onChange={this.handleInputChange} />
            <AvField
              name="email"
              label="Email"
              onChange={this.handleInputChange}
              type="email"
              required
              errorMessage="Введите валидный email"
            />
            <AvField name="inside" label="Инсайд" onChange={this.handleInputChange} />
            <AvField
              name="subdivision"
              label="Подразделение"
              onChange={this.handleInputChange}
              helpMessage="Подразделение, департамент, отдел, команда"
            />
            <Row>
              <Col>
                <Button color="primary">Создать</Button>
              </Col>
              <Col>
                <Button color="secondary" onClick={this.props.toggle}>
                  Отменить
                </Button>
              </Col>
            </Row>
          </AvForm>
        </ModalBody>
      </Modal>
    );
  }
}
export default PersonCreator;
