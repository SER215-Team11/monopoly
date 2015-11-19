package io.github.ser215_team11.monopoly.client;

/**
 * A space that contains a property of some type.
 */
public class PropertySpace implements BoardSpace {

		private Property property;

		/**
		 * Constructs a new property space that refers to the given property.
		 *
		 * @param property the property that this space represents
		 */
		public PropertySpace(Property property) {
			this.property = property;
		}

		/**
		 * Returns the name of the property this space represents.
		 *
		 * @return property name
		 */
		public String getName() {
			// TODO: Get the name from the property when it is implemented
			return "shim";
		}

		/**
		 * Returns the property this space reprsents.
		 *
		 * @return property
		 */
		public Property getProperty() {
			return property;
		}
		
		private Property[] parseConfig(String path) throws IOException {
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			String data = new String(encoded, StandardCharsets.UTF_8);

			JSONObject parent = new JSONObject(data);

			JSONArray jsonProperties = parent.getJSONArray("properties");
			Property[] out = new Property[jsonCards.length()];
			for(int i=0; i<jsonCards.length(); i++) {
				JSONObject jsonProperty = jsonProperties.getJSONObject(i);
				out[i] = new Card(jsonProperty.getString("name"),
						jsonProperty.getInt("rent"),jsonProperty.getInt("rent1"),
						jsonProperty.getInt("rent2"),jsonProperty.getInt("rent3"),
						jsonProperty.getInt("rent4"),jsonProperty.getInt("rentH"),
						jsonProperty.getInt("mortgage"),jsonProperty.getInt("houseCost"),
						jsonProperty.getInt("hotelCost"));
			}

			return out;
		}

}
