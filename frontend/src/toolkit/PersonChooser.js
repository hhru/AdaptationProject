import React from 'react';
import { CustomInput } from 'reactstrap';

class PersonChooser extends React.Component {
  personChange = event => {
    if (this.props.onChange instanceof Function) {
      this.props.onChange(event.target.value);
    }
  };

  render() {
    // Make persons options.
    let options = this.props.persons.map(person =>
      <option key={person.id} value={person.id}>
        {`${person.lastName} ${person.firstName} - ${person.email}`}
      </option>
    );
    // add an empty item to options if necessary.
    if (this.props.hasEmpty) {
      const emptyOption = <option key={''} value={''}></option>;
      options = [emptyOption].concat(options);
    }

    return (
      <CustomInput
        type="select"
        id={this.props.id}
        value={this.props.personId ? this.props.personId : ''}
        onChange={this.personChange}
      >
        {options}
      </CustomInput>
    );
  }
}

export default PersonChooser;
