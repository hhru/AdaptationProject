import React from 'react';
import ReactDOM from 'react-dom';

class AddEmployee extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      firstName: '',
      middleName: '',
      lastName: '',
      gender: '',
      position: '',
      email: '',
      employmentDate: '',
    };

    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.buildLabeledInputElement = this.buildLabeledInputElement.bind(this);
    this.buildInputElement = this.buildInputElement.bind(this);
  }

  handleInputChange(event) {
    const input = event.target;
    const value = input.type === 'checkbox' ? input.checked : input.value;
    const name = input.name;

    this.setState({
      [name]: value,
    });
  }

  handleSubmit(event) {
    event.preventDefault();
    let url = '/api/employee/';
    let data = this.state;
    //$.post(url, data, function (response) {
    //  console.log(response);
    //}.bind(this));
    alert(`Сотрудник ${data.firstName} ${data.lastName} добавлен в систему`);
  }

  buildInputElement(name, type, value, isRequired) {
    return (
      <input
        name={name}
        type={type}
        value={value}
        onChange={this.handleInputChange}
        required={isRequired}
      />
    );
  }

  buildLabeledInputElement(labelText, name, type, isRequired) {
    return (
      <span>
        <label>
          {labelText + ':'}
          {this.buildInputElement(name, type, this.state[name], isRequired)}
        </label>
        <br />
      </span>
    );
  }

  render() {
    return (
      <div>
        <form onSubmit={this.handleSubmit}>
          {this.buildLabeledInputElement('Имя', 'firstName', 'text', true)}
          {this.buildLabeledInputElement(
            'Отчество',
            'middleName',
            'text',
            false
          )}
          {this.buildLabeledInputElement('Фамилия', 'lastName', 'text', true)}
          <span>
            <label>
              Пол:
              {this.buildInputElement('gender', 'radio', 'male', true)}
              М
              {this.buildInputElement('gender', 'radio', 'female', true)}
              Ж
            </label>
            <br />
          </span>
          {this.buildLabeledInputElement(
            'Должность',
            'position',
            'text',
            false
          )}
          {this.buildLabeledInputElement('Email', 'email', 'email', true)}
          {this.buildLabeledInputElement(
            'Дата выхода',
            'employmentDate',
            'date',
            true
          )}
          <input type="submit" value="Submit" />
        </form>
      </div>
    );
  }
}

export default AddEmployee;
