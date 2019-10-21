import React, {useContext, useEffect} from 'react';
import BookService from "../../common/services/UserService";
import {Context} from "../../common/Context";

const UserPage = () => {

    const {userReservationQueue:[reservationQueue, setReservationQueue]} = useContext(Context);
    const {showUserQueue:[showUserQueue, setShowUserQueue]} = useContext(Context);

    useEffect(() => {
            BookService.getReservations({handOut: "false", returned: "false"})
                .then(response => {
                        setReservationQueue(response.data);
                    }
                );
        },[]
    );

    useEffect(() => {
            if (reservationQueue.length > 0) {
                setShowUserQueue(true);
            }
        }, [reservationQueue]
    );

    return (
        <>
            <h1 className="text-size">
                This is USER section.<br/>
                Menu options (on the left side)<br/>
                "My reservations" -> You can view your reservations: active or history;<br/>
                "Search book" -> You can search books and add to the queue;
            </h1>
        </>
    )
};

export default UserPage;