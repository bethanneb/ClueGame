/*
 * author: Elizabeth Bauch
 * author: Danella Bunavi
 */

package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	public Card() {
		cardName = null;
		cardType = null;
	}
	
	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public Card(CardType cardType, String cardName) {
		this.cardName = cardName;
		this.cardType = cardType;
	}
	
	public Card(String cardName, CardType cardType) {
		this.cardName = cardName;
		this.cardType = cardType;
	}

	public CardType getType() {
		return cardType;
	}
	

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public boolean equals(Card c){
		if (this.cardName.equals(c.cardName))
			return true;
		return false;
	}

}
