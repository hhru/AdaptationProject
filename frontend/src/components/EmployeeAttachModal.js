import React from 'react';
import axios from 'axios';
import {
  Button,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Form,
  Input,
  FormGroup,
  FormText,
  ListGroupItem,
  ListGroup,
  Col,
  Row,
} from 'reactstrap';

class EmployeeAttachModal extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      sending: false,
      fileList: [],
      file: null,
    };
    this.toggle = this.toggle.bind(this);
    this.onOpened = this.onOpened.bind(this);
    this.onChange = this.onChange.bind(this);
    this.upload = this.upload.bind(this);
    this.remove = this.remove.bind(this);
  }

  toggle() {
    this.props.parentToggle();
  }

  componentDidMount() {}

  onOpened() {
    const url = '/api/employee/' + this.props.employeeId + '/file_list';
    const self = this;
    axios
      .get(url)
      .then(function(response) {
        self.setState({
          fileList: response.data,
        });
      })
      .catch(function(error) {
        console.log(error);
      });
  }

  remove(filename) {
    if (!confirm('Вы уверены, что хотите удалить файл ' + filename + '?')) {
      return;
    }

    const url = '/api/employee/' + this.props.employeeId + '/remove';
    const self = this;

    axios
      .post(url, filename)
      .then(function(response) {
        self.state.fileList.splice(self.state.fileList.indexOf(filename), 1);
        self.forceUpdate();
      })
      .catch(function(error) {
        alert(error);
      });
  }

  upload() {
    if (this.state.file == null) {
      return;
    }
    if (this.state.fileList.includes(this.state.file.name)) {
      if (!confirm('Файл с таким именем существует. Хотите перезаписать файл?')) {
        return;
      }
    }
    if (this.state.sending) {
      return;
    }
    this.setState({
      sending: true,
    });

    const url = '/api/employee/' + this.props.employeeId + '/upload';
    const self = this;
    let formData = new FormData();
    formData.append('file', this.state.file);

    axios
      .post(url, formData, {
        headers: {
          'Content-Type': 'multipart/form-data; charset=utf-8',
        },
      })
      .then(function(response) {
        if (!self.state.fileList.includes(response.data)) {
          self.state.fileList.push(response.data);
        }
        setTimeout(() => {
          self.setState({
            sending: false,
          });
        }, 1000);
      })
      .catch(function(error) {
        setTimeout(() => {
          self.setState({
            sending: false,
          });
        }, 1000);
        alert(error);
      });
  }

  download(filename) {
    const url = '/api/employee/' + this.props.employeeId + '/download/' + filename;
    window.open(url);
  }

  onChange(e) {
    this.setState({
      file: e.target.files[0],
    });
  }

  render() {
    let self = this;
    let tasksToRender = this.state.fileList.map((filename) => (
      <ListGroupItem key={filename}>
        <Button onClick={() => this.download(filename)} color="link" value={filename}>
          {filename}
        </Button>
        <small className="text-muted file-delete" onClick={() => this.remove(filename)}>
          ✖
        </small>
      </ListGroupItem>
    ));

    return (
      <Modal
        size={'lg'}
        onOpened={this.onOpened}
        isOpen={this.props.isOpen}
        toggle={this.toggle}
        className={this.props.className}
      >
        <ModalHeader toggle={this.toggle}>Прикрепленные файлы</ModalHeader>

        <ModalBody>
          <Row>
            <Col sm={9}>
              <FormGroup>
                <Input type="file" name="file" onChange={this.onChange} />
                <FormText color="muted">можно перетащить файлы сюда</FormText>
              </FormGroup>
            </Col>

            <Col sm={3}>
              <Button color="primary" disabled={this.state.sending} onClick={this.upload}>
                Загрузить
              </Button>
            </Col>
          </Row>

          <hr />

          <ListGroup>{tasksToRender}</ListGroup>
        </ModalBody>
      </Modal>
    );
  }
}

export default EmployeeAttachModal;
