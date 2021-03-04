import React from 'react';
import Button from 'react-bootstrap/Button';
import './Stylesheets/mystyles.css';
import SupplierNavbar from './SupplierNavbar';

const supplierData = JSON.parse(localStorage.getItem('info'))

export class CreateContract extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      contractType: '', duration: '', tnc: 'Terms and Conditions',
      contractTypeError: false, durationError: false, tncError: false
    }
  }


  handleOnChange = (event) => this.setState({ [event.target.name]: event.target.value })

  handleSubmit = (event) => {
    event.preventDefault();
    let alertMessage = '';
    if (this.state.contractType === '' || this.state.contractType.length === 0) {
      this.setState({ contractTypeError: true }, console.log("ContractType Status: " + this.state.contractTypeError))
      alertMessage += "Invalid contract Type\n"

    }
    if (this.state.duration === '' || this.state.duration == null) {
      this.setState({ durationError: true }, console.log("ContractDuration: " + this.state.durationError))
      alertMessage += "Invalid duration\n"

    }
    if (this.state.tnc === '' || this.state.tnc.length === 0) {
      this.setState({ tncError: true }, console.log("tnc: " + this.state.tncError))
      alertMessage += "Invalid Terms And Conditions"

    }
    if (alertMessage !== '') {

      alert(alertMessage)

    }
    else {
      let url = 'http://localhost:8080/contract/supplier/createAndModify'
      let requestBody = {
        "contractType": this.state.contractType,
        "duration": this.state.duration,
        "tnc": this.state.tnc,
        "supplierId": JSON.parse(localStorage.getItem('info')).id,
        "status": "Waiting For Approval",
      }

      try {
        fetch(url, {
          method: 'POST',
          headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${JSON.parse(localStorage.getItem('info')).token}`
          },
          body: JSON.stringify(requestBody)
        }).then((response) => {
          if (response.status === 200) {
            return response.text()
          }
          else if (response.status === 400) {
            throw new Error("Supplier With the given Id is Not Found")
          }
          else if (response.status === 401) {
            throw new Error("UnAuthorized Access. Please login again")
          }
          else {
            throw new Error("Unknown Error Occured. Could Not Create Contract. Please Try Again")
          }
        }

        ).then((text) => {
          alert(text)
          this.props.history.push('/amenities')
        })
          .catch((e) => alert("Error: " + e))
      } catch (error) {
        console.log(error);
      }

    }

  }


  render() {

    return (
      <div>

        {supplierData.role === "supplier" ? (
          <div>
            <SupplierNavbar />
            <form onSubmit={this.handleSubmit} className="container pt-5">
              <div className="form-group text-left col-4">

                <h1 className="Col">Create Contract</h1>

                <p className="text-danger text-left">The fields marked with * are required</p>
              </div>

              <div className="form-group text-left col-4">
                <label htmlFor="ctype">Contract Type</label><label className="text-danger">*</label>
                {this.state.contractTypeError === true &&
                  <textarea
                    name="contractType"
                    maxLength="500"
                    cols="50"
                    className="form-control is-invalid"
                    id="ctype"
                    value={this.state.contractType}
                    onChange={this.handleOnChange} />}

                {this.state.contractTypeError === false &&
                  <textarea
                    name="contractType"
                    maxLength="500"
                    cols="50"
                    className="form-control"
                    id="ctype"
                    value={this.state.contractType}
                    onChange={this.handleOnChange} />}
              </div>

              <div className="form-group text-left col-4">
                <label htmlFor="cduration">Contract Duration</label><label className="text-danger">*</label>
                {this.state.durationError === true &&
                  <input
                    type="number"
                    min="1"
                    name="duration"
                    className="form-control is-invalid"
                    id="cduration"
                    value={this.state.duration}
                    onChange={this.handleOnChange} />}

                {this.state.durationError === false &&
                  <input
                    type="number"
                    min="1"
                    name="duration"
                    className="form-control"
                    id="cduration"
                    value={this.state.duration}
                    onChange={this.handleOnChange} />}
              </div>

              <div className="form-group text-left col-4">
                <label htmlFor="termsAndConditions">Terms and Conditions</label><label className="text-danger">*</label>
                {this.state.tncError === true &&
                  <textarea
                    name="tnc"
                    maxLength="500"
                    cols="50"
                    className="form-control is-invalid"
                    id="termsAndConditions"
                    value={this.state.tnc}
                    onChange={this.handleOnChange} />}

                {this.state.tncError === false &&
                  <textarea
                    name="tnc"
                    maxLength="500"
                    cols="50"
                    className="form-control"
                    id="termsAndConditions"
                    value={this.state.tnc}
                    onChange={this.handleOnChange} />}
              </div>

              <div className="form-group text-left col-4">
                <Button variant="primary" type="submit" className="mr-5">Submit</Button>
              </div>

            </form>
          </div>
        ) : (<h1>You are not Authorized</h1>)}
      </div >

    );

  }
}