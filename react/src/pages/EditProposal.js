import React, { Component } from 'react'
import axios from 'axios';
import SupplierNavbar from '../component/SupplierNavbar';

const initialState = {
    quotation: "",
    proposalDate: "",
    quotationError: "",
    proposalDateError: "",
}

export let proposalId = ""
export let proposalQuotation = ""
export let proposalDate = ""

const supplierDataInfo = JSON.parse(localStorage.getItem('info'))

export default class EditProposal extends Component {


    state = initialState

    handleSubmit = (event) => {
        event.preventDefault();
        const isValid = this.validate();
        if (isValid) {
            const supplierData = JSON.parse(localStorage.getItem('info'))
            axios.put(`http://localhost:8080/proposal/editbysupplier/${this.props.match.params.pid}?ProposalDate=${this.state.proposalDate}&quotation=${this.state.quotation}`,
                {

                },
                {
                    headers: {
                        'Authorization': `Bearer ${supplierData.token}`
                    }
                })

                .then(res => {
                    proposalQuotation = this.state.quotation;
                    proposalDate = this.state.proposalDate;
                    proposalId = this.props.match.params.pid
                    this.props.history.push('/proposaledited')
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
        console.log(event.target.name);
    }

    validate = () => {
        let proposalDateError = "";
        let quotationError = "";
        const todayDate = new Date().toISOString().slice(0, 10);

        if (Date.parse(this.state.proposalDate) < Date.parse(todayDate)) {
            proposalDateError = "Select Appropriate Delivery Date"
            this.setState({ proposalDateError })
            return false

        }
        if (this.state.quotation.length === 0) {
            quotationError = "Enter Quotation"
            this.setState({ quotationError })
            return false
        }

        return true


    }

    render() {
        return (
            <div>
                {supplierDataInfo.role === "supplier" ? (
                    <div>
                        <SupplierNavbar />

                        <div className="container">

                            <form onSubmit={this.handleSubmit}>
                                <div className="form-inner">
                                    <h2>Edit Proposal</h2>


                                    <div className="form-group">
                                        <label htmlFor="proposalDate">Proposed Delivery Date</label>
                                        <input
                                            type="date"
                                            id="proposalDate"
                                            className="form-control"
                                            name="proposalDate"
                                            placeholder="dd/mm/yyyy"
                                            value={this.state.proposalDate}
                                            onChange={this.handleChange} />
                                        <div className="error">{this.state.proposalDateError}</div>
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="quotation">Quotation</label>
                                        <input
                                            type="number"
                                            id="quotation"
                                            className="form-control"
                                            name="quotation"
                                            placeholder="enter Quotation"
                                            value={this.state.quotation}
                                            onChange={this.handleChange} />
                                        <div className="error">{this.state.quotationError}</div>
                                    </div>


                                    <button type="submit"
                                        className="btn btn-primary" >Edit</button>


                                </div>

                            </form>
                        </div>
                    </div>
                ) : (<h1>You are not Authorized</h1>)}


            </div>
        )
    }

}