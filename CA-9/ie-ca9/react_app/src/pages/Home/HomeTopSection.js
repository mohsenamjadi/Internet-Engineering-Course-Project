import * as React from "react";
import CoverPhoto from '../../Assets/images/CoverPhoto.jpg';

export default class HomeTopSection extends React.Component {
    render() {
        return (
            <div className="app-background">
                <div className="over-img">
                    <img alt="" src={CoverPhoto} width={'100%'} />
                </div>
            </div>
        );
    }
}