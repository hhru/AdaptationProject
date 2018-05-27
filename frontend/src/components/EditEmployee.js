import { FEMALE, MALE } from '../constants/gender';

import GenderEdit from '../toolkit/GenderEdit';
import PersonEdit from '../toolkit/PersonEdit';
import LabeledInput from '../toolkit/LabeledInput';
import PersonChooser from '../toolkit/PersonChooser';
import PersonCreator from '../toolkit/PersonCreator';
import Person from '../toolkit/Person';
import Employee from '../toolkit/Employee';
import User from '../toolkit/User';

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

class EditEmployee extends React.Component {
  state = {
    firstName: '',
    lastName: '',
    middleName: '',
    email: '',
    inside: '',
    gender: FEMALE,
    position: '',
    employmentDate: '',
    hrId: null,
    chiefId: null,
    mentorId: null,
    persons: [],
    users: [],
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
      this.setState({ chiefId: newPerson.id });
    };
    const failed = (error) => {
      console.log(error);
      alert(error);
    };

    this.personCreate(person, updateChief, failed);
  };

  handleMentorCreate = (person) => {
    this.toggleChiefCreator();

    const updateMentor = (newPerson) => {
      console.log(newPerson);
      this.setState({ mentorId: newPerson.id });
    };
    const failed = (error) => {
      console.log(error);
      alert(error);
    };

    this.personCreate(person, updateMentor, failed);
  };

  handleEmployeeChange = (employee) => {
    const {
      firstName,
      lastName,
      middleName,
      email,
      inside,
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

  handleHrChange = (hrId) => {
    this.setState({ hrId: hrId });
  };

  handleDismissEmployee = (event) => {
    event.preventDefault();

    const employeeId = this.props.match.params.id;
    axios
      .delete('/api/employee/' + employeeId + '/dismiss')
      .then((response) => this.props.history.push('/list_employees'))
      .catch((error) => {
        console.log(error);
        alert(error);
      });
  };

  handleUpdateEmployee = (event) => {
    event.preventDefault();

    const employeeId = this.props.match.params.id;

    const {
      firstName,
      lastName,
      middleName,
      email,
      inside,
      gender,
      position,
      employmentDate,
      chiefId,
      mentorId,
      hrId,
    } = this.state;

    const employeeUpdateDto = {
      id: employeeId,
      self: {
        firstName: firstName,
        lastName: lastName,
        middleName: middleName,
        email: email,
        inside: inside,
      },
      chief: { id: chiefId },
      mentor: mentorId ? { id: mentorId } : null,
      hrId: hrId,
      gender: gender,
      position: position,
      employmentDate: employmentDate,
    };

    this.employeeUpdate(employeeUpdateDto);
  };

  componentDidMount() {
    const failed = (error) => {
      console.log(error);
      alert(error);
    };

    const updateEmployee = (employeeDto) => {
      console.log(employeeDto);
      this.setState({
        firstName: employeeDto.employee.firstName,
        lastName: employeeDto.employee.lastName,
        middleName: employeeDto.employee.middleName,
        email: employeeDto.employee.email,
        inside: employeeDto.employee.inside,
        gender: employeeDto.gender,
        position: employeeDto.position,
        employmentDate: employeeDto.employmentDate,
        chiefId: employeeDto.chief.id,
        mentorId: employeeDto.mentor ? employeeDto.mentor.id : undefined,
        hrId: employeeDto.hr.id,
      });
    };

    const updatePersons = (persons) => {
      this.setState({ persons: persons });
    };

    const updateUsers = (users) => {
      this.setState({ users: users });
    };

    const employeeId = this.props.match.params.id;
    this.getEmployee(employeeId, updateEmployee, failed);

    this.listPersons(updatePersons, failed);
    this.listUsers(updateUsers, failed);
  }

  getEmployee(employeeId, completed, failed) {
    axios
      .get('/api/employee/' + employeeId)
      .then((response) => completed(response.data))
      .catch((error) => failed(error));
  }

  listUsers(completed, failed) {
    axios
      .get('/api/users/all')
      .then((response) => completed(response.data))
      .catch((error) => failed(error));
  }

  listPersons(completed, failed) {
    axios
      .get('/api/personal/all')
      .then((response) => completed(response.data))
      .catch((error) => failed(error));
  }

  personCreate(person, completed, failed) {
    const updatePersons = (persons) => this.setState({ persons: persons });

    axios
      .post('/api/personal/create', person)
      .then((response) => {
        this.listPersons(updatePersons, failed);
        completed(response.data);
      })
      .catch((error) => failed(error));
  }

  employeeUpdate(employeeUpdateDto) {
    axios
      .put('/api/employee/update', employeeUpdateDto)
      .then((response) => this.props.history.push('/employee/' + response.data.id))
      .catch((error) => {
        console.log(error);
        alert(error);
      });
  }

  render() {
    return (
      <Container>
        <PersonCreator
          title="Создание начальника"
          modal={this.state.chiefModal}
          toggle={this.toggleChiefCreator}
          onCreate={this.handleChiefCreate}
        />
        <PersonCreator
          title="Создание ментора"
          modal={this.state.mentorModal}
          toggle={this.toggleMentorCreator}
          onCreate={this.handleMentorCreate}
        />

        <Form>
          <FormGroup row>
            <h3>Редактирование сотрудника</h3>
          </FormGroup>
          <Employee
            firstName={this.state.firstName}
            lastName={this.state.lastName}
            middleName={this.state.middleName}
            email={this.state.email}
            inside={this.state.inside}
            gender={this.state.gender}
            position={this.state.position}
            employmentDate={this.state.employmentDate}
            onChange={this.handleEmployeeChange}
          />
          <Person
            id="chief"
            persons={this.state.persons}
            title="Начальник"
            personId={this.state.chiefId}
            onChange={this.handleChiefChange}
            onAdd={this.toggleChiefCreator}
          />
          <Person
            id="mentor"
            persons={this.state.persons}
            title="Ментор"
            personId={this.state.mentorId}
            hasEmpty={true}
            onChange={this.handleMentorChange}
            onAdd={this.toggleMentorCreator}
          />
          <User
            id="hr"
            persons={this.state.users}
            title="HR"
            personId={this.state.hrId}
            onChange={this.handleHrChange}
          />

          <FormGroup row>
            <Col sm={{ size: 1, order: 2, offset: 2 }}>
              <Button onClick={this.handleUpdateEmployee} color="primary">
                {'Применить'}
              </Button>
            </Col>
            <Col sm={{ size: 2, order: 3, offset: 7 }}>
              <Button block onClick={this.handleDismissEmployee} color="danger">
                {'Уволить'}
              </Button>
            </Col>
          </FormGroup>
        </Form>
      </Container>
    );
  }
}

export default EditEmployee;
