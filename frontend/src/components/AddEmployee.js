import { FEMALE, MALE } from '../constants/gender';

import GenderEdit from '../toolkit/GenderEdit';
import PersonEdit from '../toolkit/PersonEdit';
import LabeledInput from '../toolkit/LabeledInput';
import PersonChooser from '../toolkit/PersonChooser';
import PersonCreator from '../toolkit/PersonCreator';
import Person from '../toolkit/Person';
import Employee from '../toolkit/Employee';

import React from 'react';
import {
  Col,
  Button,
  Form,
  FormGroup,
  Label,
  Input,
  InputGroup,
  InputGroupAddon,
  FormText,
  Container,
} from 'reactstrap';
import axios from 'axios';

class AddEmployee extends React.Component {
  state = {
    firstName: '',
    lastName: '',
    middleName: '',
    email: '',
    inside: '',
    subdivision: '',
    gender: FEMALE,
    position: '',
    employmentDate: '',
    chiefId: null,
    mentorId: null,
    persons: [],
    chiefModal: false,
    mentorModal: false,
  };

  toggleChiefCreator = (event) => {
    if (event) {
      event.preventDefault();
    }

    this.setState({
      chiefModal: !this.state.chiefModal,
    });
  };

  toggleMentorCreator = (event) => {
    if (event) {
      event.preventDefault();
    }

    this.setState({
      mentorModal: !this.state.mentorModal,
    });
  };

  handleChiefCreate = (person) => {
    this.toggleChiefCreator();

    const updateChief = (newPerson) => {
      console.log(newPerson);
      this.setState({
        chiefId: newPerson.id,
      });
    };
    const failed = (error) => {
      console.log(error);
    };

    this.createPerson(person, updateChief, failed);
  };

  handleMentorCreate = (person) => {
    this.toggleMentorCreator();

    const updateMentor = (newPerson) => {
      console.log(newPerson);
      this.setState({ mentorId: newPerson.id });
    };
    const failed = (error) => {
      console.log(error);
    };

    this.createPerson(person, updateMentor, failed);
  };

  handleEmployeeChange = (employee) => {
    const {
      firstName,
      lastName,
      middleName,
      email,
      inside,
      subdivision,
      gender,
      employmentDate,
      position,
    } = employee;

    this.setState({
      firstName: firstName,
      lastName: lastName,
      middleName: middleName,
      email: email,
      inside: inside,
      subdivision: subdivision,
      gender: gender,
      employmentDate: employmentDate,
      position: position,
    });
  };

  handleChiefChange = (chiefId) => {
    this.setState({ chiefId: chiefId });
  };

  handleMentorChange = (mentorId) => {
    this.setState({ mentorId: mentorId ? mentorId : null });
  };

  handleCreateEmployee = (event) => {
    event.preventDefault();

    const {
      firstName,
      lastName,
      middleName,
      email,
      inside,
      subdivision,
      gender,
      position,
      employmentDate,
      chiefId,
      mentorId,
    } = this.state;

    const employee = {
      self: {
        firstName: firstName,
        lastName: lastName,
        middleName: middleName,
        email: email,
        inside: inside,
        subdivision: subdivision,
      },
      gender: gender,
      position: position,
      employmentDate: employmentDate,
      chiefId: chiefId,
      mentorId: mentorId ? mentorId : null,
    };

    this.createEmployee(employee);
  };

  componentDidMount() {
    this.listPersons(
      (persons) => {
        const chiefId = Array.isArray(persons) && persons.length > 0 ? persons[0].id : null;
        this.setState({
          persons: persons,
          chiefId: chiefId,
        });
      },
      (error) => {
        console.log(error);
        alert(error);
      }
    );
  }

  listPersons(completed, failed) {
    axios
      .get('/api/personal/all')
      .then((response) => {
        completed(response.data);
      })
      .catch((error) => {
        failed(error);
      });
  }

  createPerson(person, completed, failed) {
    const updatePersons = (personsList) => {
      this.setState({
        persons: personsList,
      });
    };

    axios
      .post('/api/personal/create', person)
      .then((response) => {
        this.listPersons(updatePersons, failed);
        completed(response.data);
      })
      .catch((error) => {
        failed(error);
      });
  }

  createEmployee(employee) {
    axios
      .post('/api/employee/create', employee)
      .then((response) => {
        this.props.history.push('/employee/' + response.data.id);
      })
      .catch((error) => {
        console.log(error);
        alert(error);
      });
  }

  render() {
    return (
      <Container>
        <PersonCreator
          title="Создание руководителя"
          modal={this.state.chiefModal}
          toggle={this.toggleChiefCreator}
          onCreate={this.handleChiefCreate}
        />
        <PersonCreator
          title="Создание куратора"
          modal={this.state.mentorModal}
          toggle={this.toggleMentorCreator}
          onCreate={this.handleMentorCreate}
        />

        <Form>
          <FormGroup row>
            <h3>Создание сотрудника</h3>
          </FormGroup>
          <Employee
            firstName={this.state.firstName}
            lastName={this.state.lastName}
            middleName={this.state.middleName}
            email={this.state.email}
            inside={this.state.inside}
            subdivision={this.state.subdivision}
            gender={this.state.gender}
            position={this.state.position}
            employmentDate={this.state.employmentDate}
            onChange={this.handleEmployeeChange}
          />
          <Person
            id="chief"
            persons={this.state.persons}
            title="Руководитель"
            personId={this.state.chiefId}
            onChange={this.handleChiefChange}
            onAdd={this.toggleChiefCreator}
          />
          <Person
            id="mentor"
            persons={this.state.persons}
            title="Куратор"
            personId={this.state.mentorId}
            hasEmpty={true}
            onChange={this.handleMentorChange}
            onAdd={this.toggleMentorCreator}
          />

          <FormGroup row>
            <Col sm={{ size: 6, order: 2, offset: 4 }} lg={{ size: 6, order: 2, offset: 2 }}>
              <Button onClick={this.handleCreateEmployee} color="primary">
                {'Создать сотрудника'}
              </Button>
            </Col>
          </FormGroup>
        </Form>
      </Container>
    );
  }
}

export default AddEmployee;
