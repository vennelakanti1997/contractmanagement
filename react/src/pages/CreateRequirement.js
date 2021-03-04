import React, { Component } from 'react'
import axios from 'axios';
import AdminNavbar from '../component/AdminNavbar';

const initialState = {
    description: "",
    deliveryDate: "",
    type: "",
    descriptionError: "",
    deliveryDateError: "",
    typeError: ""
}

export let requirementId = ""
export let requirementDescription = ""
export let requirementDeliveryDate = ""
export let requirementType = ""
const adminData = JSON.parse(localStorage.getItem('info'))

export default class CreateRequirment extends Component {

    state = initialState

    handleSubmit = (event) => {
        event.preventDefault();
        const isValid = this.validate();
        if (isValid) {
            const typeId = this.state.type;
            axios.post(`http://localhost:8080/requirement/create/${typeId}`,
                {
                    description: this.state.description,
                    deliveryDate: this.state.deliveryDate,
                }, {
                headers: {
                    'Authorization': `Bearer ${adminData.token}`
                }
            })

                .then(res => {
                    requirementDescription = this.state.description;
                    requirementDeliveryDate = this.state.deliveryDate;
                    requirementType = this.state.type;
                    this.props.history.push('/requirementdetails')
                })
                .catch(err => {
                    console.log(err.response)
                })
        }
    }

    handleChange = (event) => {
        this.setState({
            [event.target.name]: event.target.value
        })
    }

    validate = () => {

        const todayDate = new Date().toISOString().slice(0, 10);
        let descriptionError = "";
        let deliveryDateError = "";
        let typeError = "";

        if (this.state.description.length <= 4) {
            descriptionError = "Minimum length 4"
            this.setState({ descriptionError })
            return false
        } else {
            this.setState({
                nameError: "",
            })
        }

        if (Date.parse(this.state.deliveryDate) < Date.parse(todayDate)) {
            deliveryDateError = "Select Appropriate Delivery Date"
            this.setState({ deliveryDateError })
            return false

        }
        if (this.state.type.length === 0) {
            typeError = "Requirement must have a type"
            this.setState({ typeError })
            return false
        } else {
            this.setState({
                typeError: "",
            })
        }
        return true
    }

    render() {
        return (
            <div>
                {adminData.role === "admin" ? (
                    <div>
                        <AdminNavbar />

                        <div className="container">

                            <form onSubmit={this.handleSubmit}>
                                <div className="form-inner">
                                    <h2>Create Requirement</h2>


                                    <div className="form-group">
                                        <label htmlFor="deliveryDate">Delivery Date</label>

                                        <input
                                            type="date"
                                            id="deliveryDate"
                                            className="form-control"
                                            name="deliveryDate"
                                            placeholder="enter delivery date (dd/mm/yyyy)"
                                            value={this.state.deliveryDate}
                                            onChange={this.handleChange} />
                                        <div className="error">{this.state.deliveryDateError}</div>
                                    </div>
                                    <div className="form-group">
                                        <div>
                                            <label htmlFor="description" >Description</label>
                                            <textarea
                                                id="description"
                                                name="description"
                                                className="form-control"
                                                onChange={this.handleChange}
                                                value={this.state.description}
                                                style={{ "verticalAlign": "middle" }} />
                                            <div className="error">{this.state.descriptionError}</div>
                                        </div>
                                    </div>
                                    <div className="form-group">
                                        <div>
                                            <label style={{ "padding": "10px 10px 10px 0" }}>Requirement Type</label>
                                            <select
                                                name="type"
                                                value={this.state.type}
                                                onChange={this.handleChange} >
                                                <option selected>---Select type---</option>
                                                <option value="1">Type 1</option>
                                                <option value="2">Type 2</option>
                                                <option value="3">Type 3</option>
                                            </select>
                                            <div className="error">{this.state.typeError}</div>
                                        </div>
                                    </div>

                                    <button type="submit"
                                        className="btn btn-primary" >Create</button>


                                </div>

                            </form>
                        </div>
                    </div>


                ) : (<h1>You are not Authorized</h1>)}



            </div>
        )
    }

}

