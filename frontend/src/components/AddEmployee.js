import { FEMALE, MALE } from '../constants/gender';

import GenderEdit from '../toolkit/GenderEdit';
import PersonEdit from '../toolkit/PersonEdit';
import LabeledInput from '../toolkit/LabeledInput';
import PersonChooser from '../toolkit/PersonChooser';
import PersonCreator from '../toolkit/PersonCreator';
import Person from '../toolkit/Person';
import Employee from '../toolkit/Employee';

import { AvForm, AvField, AvRadioGroup, AvRadio } from 'availity-reactstrap-validation';

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
} from 'reactstrap';
import axios from 'axios';

class AddEmployee extends React.Component {
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
    chiefId: null,
    chiefValid: true,
    mentorId: null,
    persons: [],
    chiefModal: false,
    mentorModal: false,
  };

  toggleChiefCreator = () => {
    this.setState({
      chiefModal: !this.state.chiefModal,
    });
  };

  toggleMentorCreator = () => {
    this.setState({
      mentorModal: !this.state.mentorModal,
    });
  };

  handleChiefCreate = (person) => {
    this.toggleChiefCreator();

    const updateChief = (newPerson) => {
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
      interimDate,
      finalDate,
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
      interimDate: interimDate,
      finalDate: finalDate,
      position: position,
    });
  };

  handleChiefChange = (chiefId) => {
    this.setState({ chiefId });
  };

  handleMentorChange = (mentorId) => {
    this.setState({ mentorId: mentorId ? mentorId : null });
  };

  handleChiefValidChange = (chiefId) => {
    this.setState({ chiefValid: Boolean(chiefId) });
  };

  handleCreateEmployee = () => {
    if (!this.state.chiefId) {
      this.setState({ chiefValid: false });
      return;
    }

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
      interimDate,
      finalDate,
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
      interimDate: interimDate,
      finalDate: finalDate,
      chiefId: chiefId,
      mentorId: mentorId ? mentorId : null,
    };

    this.createEmployee(employee);
  };

  componentDidMount() {
    this.listPersons(
      (persons) => {
        this.setState({
          persons: persons,
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
    if (this.state.disableSend) {
      return;
    }
    this.state.disableSend = true;

    axios
      .post('/api/employee/create', employee)
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

  clickFunc = (e) => {
    switch (e) {
      case 'valid':
        this.handleCreateEmployee();
        break;
      case 'chiefCreate':
        this.toggleChiefCreator();
        break;
      case 'mentorCreate':
        this.toggleMentorCreator(this);
        break;
    }
  };

  render() {
    const defaultValues = {
      genderRadioGroup: MALE,
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

        <AvForm
          model={defaultValues}
          onValidSubmit={() => this.clickFunc('valid')}
          onInvalidSubmit={() => {}}
        >
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
            interimDate={this.state.interimDate}
            finalDate={this.state.finalDate}
            onChange={this.handleEmployeeChange}
          />
          <Person
            id="chief"
            persons={this.state.persons}
            title="Руководитель"
            personId={this.state.chiefId}
            chiefValid={this.state.chiefValid}
            onValid={this.handleChiefValidChange}
            required={true}
            onChange={this.handleChiefChange}
            onAdd={() => this.clickFunc('chiefCreate')}
          />
          <Person
            id="mentor"
            persons={this.state.persons}
            title="Куратор"
            personId={this.state.mentorId}
            chiefValid={true}
            required={false}
            onChange={this.handleMentorChange}
            onAdd={() => this.clickFunc('mentorCreate')}
          />

          <Row className="mt-4">
            <Col sm={{ size: 6, offset: 5 }}>
              <FormGroup>
                <Button color="primary">{'Создать сотрудника'}</Button>
              </FormGroup>
            </Col>
          </Row>
        </AvForm>
      </Container>
    );
  }
}

export default AddEmployee;
