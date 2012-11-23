package reformyourcountry.model;
/**
 * interface for common method with EL in pages with vote
 * @see argument.jsp
 * @see goodexample.jsp
 * @author maxime
 *
 */
public interface IVote {

	public int getTotal();
	public int getVoteValueByUser(User user);
}
