import React from 'react';
import { Form, Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import PersonEdit from './PersonEdit';

class PersonCreator extends React.Component {
  state = {
    firstName: '',
    lastName: '',
    middleName: '',
    email: '',
    inside: ''
  };

  handlePersonChange = person => {
    this.setState(person);
  };

  handlePersonCreate = event => {
    event.preventDefault();

    if (this.props.onCreate instanceof Function) {
      this.props.onCreate({
        firstName: this.state.firstName,
        lastName: this.state.lastName,
        middleName: this.state.middleName,
        email: this.state.email,
        inside: this.state.inside
      });
    }

    // clear data
    this.setState({
      firstName: '',
      lastName: '',
      middleName: '',
      email: '',
      inside: ''
    });
  };

  render() {
    return (
      <Modal isOpen={this.props.modal} toggle={this.props.toggle}>
        <ModalHeader toggle={this.props.toggle}>
          {this.props.title}
        </ModalHeader>
        <ModalBody>
          <Form>
            <PersonEdit
              firstName={this.state.firstName}
              lastName={this.state.lastName}
              middleName={this.state.middleName}
              email={this.state.email}
              inside={this.state.inside}
              onChange={this.handlePersonChange}
            />
          </Form>
        </ModalBody>
        <ModalFooter>
          <Button color="primary" onClick={this.handlePersonCreate}>Создать</Button>
          {' '}
          <Button color="secondary" onClick={this.props.toggle}>Отменить</Button>
        </ModalFooter>
      </Modal>
    );
  }
}

export default PersonCreator;
