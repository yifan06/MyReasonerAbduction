/*
  Copyright 2011 Wu, Kejia (w_kejia{at}cs.concordia.ca)

  This file is part of Deslog.

  Deslog is free software: you can redistribute it and/or modify it under the
  terms of the GNU General Public License as published by the Free Software
  Foundation, either version 3 of the License, or (at your option) any later
  version.

  Deslog is distributed in the hope that it will be useful, but WITHOUT ANY
  WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
  PARTICULAR PURPOSE. See the GNU General Public License for more details.

  You should have received a copy of the GNU General Public License along with
  Deslog. If not, see <http://www.gnu.org/licenses/>.
 */
package yifan.dl.facility;

/**
 * This class restricts a type of classes that must provide a deep copy
 * interface.
 * <p>
 * Instead of clone function, which is difficult to be handled in the case of
 * generic programming, IDeepCopy simplifies the deep copy operation.
 */
public interface IDeepCopy<T extends IDeepCopy<T>> {

	public T deepCopy (Object... objects);
}
