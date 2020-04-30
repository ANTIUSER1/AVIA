package ru.integrotech.su.inputparams.attractionAB;

import java.util.Collections;

/**
 *
 * container for attractionABRequest (request body for attractionAB project)
 *
 * data ( private Data data; private String lang; )
 */

public class AttractionAbInput {

    /**
     *  Static constructor. Use in tests. Create
     *  AttractionAbInput with single segment
     * */
	public static AttractionAbInput  of(String airlineIATA,
										String originIATA,
										String destinationIATA,
										String bookingClassCode,
										String fareBasisCode,
										String ticketDesignator,
			                            String tierCode) {

	    AttractionAbInput result = new AttractionAbInput();
        Data data = new Data();
        Segment segment = new Segment();
        segment.setAirlineIATA(airlineIATA);
        segment.setOriginIATA(originIATA);
        segment.setDestinationIATA(destinationIATA);
        segment.setBookingClassCode(bookingClassCode);
        segment.setFareBasisCode(fareBasisCode);
        segment.setTicketDesignator(ticketDesignator);
        data.setSegments(Collections.singletonList(segment));
        data.setTierCode(tierCode);
        result.setData(data);
        return result;
    }

	private Data data;

	private String lang;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

}
