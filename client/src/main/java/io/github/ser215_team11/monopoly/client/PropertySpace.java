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
		 * Returns the property this space represents.
		 *
		 * @return property
		 */
		public Property getProperty() {
			return property;
		}

}
