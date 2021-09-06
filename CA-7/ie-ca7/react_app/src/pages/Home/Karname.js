import * as React from "react";
import KarnameChargeWrapper from "./KarnameChargeWrapper";

export default class Karname extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
        };
    }

    renderKarnameChargeWrappers() {
        return this.props.terms.map((term) =>
            <KarnameChargeWrapper term={term} key={term.term}/>
        );
    }

    render() {
        return (

            <div className="col-lg-9 col-6 karname-list-container">
                <div className="row">
                    {this.renderKarnameChargeWrappers()}
                </div>
            </div>


        );
    }
}