import * as React from "react";
import KarnameItem from "./KarnameItem";

export default class KarnameChargeWrapper extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
        };
    }

    renderKarnameItems() {
        return this.props.term.grades.map((grade, index) =>
            <KarnameItem grade={grade} key={grade.code} index={index}/>
        );
    }

    render() {
        return (
            <>
                <div className="karname-charge-wrapper">
                    <div className="karname-charge">
                        <div className="selector row" style={{right: '80px'}}>
                            <div className="selector-item active-item">
                            <span>
                                کارنامه ترم {this.props.term.term}
                            </span>
                            </div>
                        </div>
                        <div className="karnameha">
                            {this.renderKarnameItems()}
                            <div className="row">
                                <div className="col-2 mr-auto">
                                    <div className="moadel-box">
                                     <span>
                                         معدل : {this.props.term.moadel.toFixed(2)}
                                     </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </>
        );
    }
}