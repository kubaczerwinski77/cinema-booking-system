export interface ISeat {
  id: number;
  rowInHall: number;
  columnInHall: number;
  seatType: {
    id: number;
    type: "STANDARD" | "PREMIUM" | "EMPTY";
    price: number;
  };
  cinemaHall: {
    id: number;
    name: string;
    totalSize: number;
  };
}
