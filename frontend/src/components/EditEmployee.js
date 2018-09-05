import { FEMALE, MALE } from '../constants/gender';

import GenderEdit from '../toolkit/GenderEdit';
import PersonEdit from '../toolkit/PersonEdit';
import LabeledInput from '../toolkit/LabeledInput';
import PersonChooser from '../toolkit/PersonChooser';
import PersonCreator from '../toolkit/PersonCreator';
import Person from '../toolkit/Person';
import Employee from '../toolkit/Employee';
import User from '../toolkit/User';

import { AvForm } from 'availity-reactstrap-validation';

import React from 'react';
import {
  Row,
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
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
} from 'reactstrap';
import axios from 'axios';

class EditEmployee extends React.Component {
  state = {
    disableSend: false,
    firstName: '',
    lastName: '',
    middleName: '',
    email: '',
    inside: '',
    subdivision: '',
    gender: MALE,
    position: '',
    employmentDate: '',
    interimDate: '',
    finalDate: '',
    hrId: null,
    chiefId: null,
    mentorId: null,
    persons: [],
    users: [],
    chiefModal: false,
    dismissed: false,
    mentorModal: false,
    dismissModal: false,
    dismissCommentValue: '',
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

  toggleDismissModal = () => {
    this.setState({
      dismissModal: !this.state.dismissModal,
    });
  };

  onDismissCommentChange = (event) => {
    this.setState({
      dismissCommentValue: event.target.value,
    });
  };

  handleChiefCreate = (person) => {
    this.toggleChiefCreator();

    const updateChief = (newPerson) => {
      this.setState({ chiefId: newPerson.id });
    };
    const failed = (error) => {
      console.log(error);
    };

    this.personCreate(person, updateChief, failed);
  };

  handleMentorCreate = (person) => {
    this.toggleMentorCreator();

    const updateMentor = (newPerson) => {
      this.setState({ mentorId: newPerson.id });
    };
    const failed = (error) => {
      console.log(error);
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
      subdivision,
      gender,
      employmentDate,
      interimDate,
      finalDate,
      position,
      dismissed,
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
      interimDate: interimDate,
      finalDate: finalDate,
      position: position,
      dismissed: dismissed,
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
    this.toggleDismissModal();
    const employeeId = this.props.match.params.id;
    const dismissComment = this.state.dismissCommentValue;
    const url = '/api/employee/' + employeeId + (this.state.dismissed ? '/undismiss' : '/dismiss');

    axios
      .post(url, dismissComment)
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
      dismissed,
      firstName,
      lastName,
      middleName,
      email,
      inside,
      subdivision,
      gender,
      position,
      employmentDate,
      interimDate,
      finalDate,
      chiefId,
      mentorId,
      hrId,
    } = this.state;

    const employeeUpdateDto = {
      id: employeeId,
      dismissed: dismissed,
      self: {
        firstName: firstName,
        lastName: lastName,
        middleName: middleName,
        email: email,
        inside: inside,
        subdivision: subdivision,
      },
      chiefId: chiefId,
      mentorId: mentorId ? mentorId : null,
      hrId: hrId,
      gender: gender,
      position: position,
      employmentDate: employmentDate,
      interimDate: interimDate,
      finalDate: finalDate,
    };

    this.employeeUpdate(employeeUpdateDto);
  };

  componentDidMount() {
    const failed = (error) => {
      console.log(error);
      alert(error);
    };

    const updateEmployee = (employeeDto) => {
      this.setState({
        dismissed: employeeDto.dismissed,
        firstName: employeeDto.employee.firstName,
        lastName: employeeDto.employee.lastName,
        middleName: employeeDto.employee.middleName,
        email: employeeDto.employee.email,
        inside: employeeDto.employee.inside,
        subdivision: employeeDto.employee.subdivision,
        gender: employeeDto.gender,
        position: employeeDto.position,
        employmentDate: employeeDto.employmentDate,
        interimDate: employeeDto.interimDate,
        finalDate: employeeDto.finalDate,
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
    if (this.state.disableSend) {
      return;
    }
    this.state.disableSend = true;

    axios
      .put('/api/employee/update', employeeUpdateDto)
      .then((response) => {
        this.props.history.push('/employee/' + response.data.id);
        this.state.disableSend = false;
      })
      .catch((error) => {
        console.log(error);
        alert(error);
        this.state.disableSend = false;
      });
  }

  render() {
    const dismissed = this.state.dismissed;
    const defaultValues = {
      genderRadioGroup: this.state.gender,
    };

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

        <AvForm model={defaultValues}>
          <FormGroup row>
            <h3>Редактирование сотрудника</h3>
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
            interimDate={this.state.interimDate}
            finalDate={this.state.finalDate}
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
          <User
            id="hr"
            persons={this.state.users}
            title="HR"
            personId={this.state.hrId}
            onChange={this.handleHrChange}
          />

          <Row className="mt-4">
            <Col sm={{ size: 2, offset: 1 }}>
              <Button block onClick={this.toggleDismissModal} color={dismissed ? 'info' : 'danger'}>
                {dismissed ? 'Восстановить' : 'ИС не пройден'}
              </Button>

              <Modal isOpen={this.state.dismissModal} toggle={this.toggleDismissModal}>
                <ModalHeader toggle={this.toggleDismissModal}>
                  {dismissed ? 'Восстановление сотрудника' : 'Увольнение сотрудника'}
                </ModalHeader>

                <ModalBody>
                  <Form>
                    <FormGroup>
                      <Input
                        type="text"
                        name="text"
                        placeholder="Написать комментарий"
                        onChange={this.onDismissCommentChange}
                        value={this.state.dismissCommentValue}
                      />
                    </FormGroup>
                  </Form>
                </ModalBody>

                <ModalFooter>
                  <Button
                    outline
                    color={dismissed ? 'info' : 'danger'}
                    onClick={this.handleDismissEmployee}
                  >
                    {dismissed ? 'Восстановить' : 'ИС не пройден'}
                  </Button>
                  <Button outline color="secondary" onClick={this.toggleDismissModal}>
                    Отменить
                  </Button>
                </ModalFooter>
              </Modal>
            </Col>

            <Col sm={{ size: 2, offset: 7 }}>
              <Button onClick={this.handleUpdateEmployee} color="primary">
                {'Применить'}
              </Button>
            </Col>
          </Row>
        </AvForm>
      </Container>
    );
  }
}

export default EditEmployee;
